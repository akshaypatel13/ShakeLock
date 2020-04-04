package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_set_security_key.*
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.io.InputStream
import java.math.BigInteger

import java.security.MessageDigest


class SetSecurityKey : AppCompatActivity() {

    private val TAG = "NewSecretKeyActivity"
    private val authenticateUser = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_security_key)

        try{
            var inp_st: InputStream =assets.open("app.gif")
            var bts:ByteArray= IOUtils.toByteArray(inp_st)
            gi.setBytes(bts)
            gi.startAnimation()
        }catch(exc: IOException){
        }
        val emailField =findViewById<EditText>(R.id.recipientEId)
        val secretKeyField = findViewById<EditText>(R.id.subjectLine)
        val repeatKeyField = findViewById<EditText>(R.id.message)
        val setKeyBtn = findViewById<Button>(R.id.setpass)


        setKeyBtn.setOnClickListener {
            val email =emailField.text.toString()
            val secretKeyVal = secretKeyField.text.toString()
            val repeatKeyVal = repeatKeyField.text.toString()

            /* Validate user input before processing. */
            if(checkEmail(email) && checkSecretKey(secretKeyVal,repeatKeyVal)){
                val keywithMd5 = secretKeyVal.md5()
                Log.d(keywithMd5,"PasswordWithmd5 : (SignUp)")
                authenticateUser.createUserWithEmailAndPassword(email,keywithMd5)
                    .addOnCompleteListener(this){
                            task ->
                        if (task.isSuccessful){
                            Log.d(TAG, "createSecretKeyWithEmail:success")
                            val user = authenticateUser.currentUser
                            var intent = Intent(applicationContext,Home::class.java)
                            intent.putExtra("message","Authentication Successful!!!!")
                            startActivity(intent)
                        }
                        else{
                            Log.w(TAG, "createSecretKeyWithEmail:failure", task.exception)
                            val intent = Intent(applicationContext,Dashboard::class.java)
                            intent.putExtra("message","Authentication Error!!!Key already exists")
                            startActivity(intent)
                        }
                    }

            }
        }
    }

    /**
     * @param email string pulled from email field.
     * @return Boolean indicating pass or fail.
     */
    private fun checkEmail(email: String): Boolean {
        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            findViewById<EditText>(R.id.recipientEId).error = "Please enter a valid email address."
            return false
        }
        else return true
    }

    /**
     * @param secretKey string pulled from password field.
     * @param repeatKey re-entered user password.
     * @return Boolean indicating pass or fail.
     */
    private fun checkSecretKey(secretKeyVal: String, repeatKeyVal: String): Boolean {
        if (secretKeyVal.isEmpty()) {
            findViewById<EditText>(R.id.subjectLine).error = "Please enter a secret key."
            return false
        }
        else if (secretKeyVal.length < 6) {
            findViewById<EditText>(R.id.subjectLine).error = "Please enter a secret key with more than six characters."
            return false
        }
        else if (secretKeyVal != repeatKeyVal) {
            findViewById<EditText>(R.id.message).error = "Keys do not match."
            return false
        }
        else return true
    }
    /* Hide keyboard when user touches outside of EditText. */
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }
}
