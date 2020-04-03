package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_input_pattern.*
import kotlinx.android.synthetic.main.activity_setup_password.*
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.io.InputStream
import java.util.regex.Pattern

class SetupPassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup_password)

        try{
            var inp_st: InputStream =assets.open("app.gif")
            var bts:ByteArray= IOUtils.toByteArray(inp_st)
            gifImage4.setBytes(bts)
            gifImage4.startAnimation()
        }catch(exc: IOException){
        }


        //https://stackoverflow.com/questions/12586340/regex-to-find-special-characters-in-java/12586471
        //https://regexr.com/
        var final=Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[\\\$&+,:;=?@#| ]).{8,}")
        password_setup.addTextChangedListener(object :TextWatcher{

            override fun afterTextChanged(s: Editable?) {
                var password_set=password_setup.text.toString()
                var matcher=final.matcher(password_set)
                if(matcher.find()){
                    password.error=""
                }else{
                    password.error="abcd"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                password.error=""
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                password.error=""
            }

        })


        password_reenter_setup.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                var pass_first=password_setup.text.toString()
                var pass_second=password_reenter_setup.text.toString()
                if(pass_second!!.equals(pass_first)){
                    reenter_password.error=""
                }else{
                    reenter_password.error="not match"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })



        submit.setOnClickListener {
            val check_user: SharedPreferences =getSharedPreferences("user_log",0)
            val editor: SharedPreferences.Editor=check_user.edit()
            val pass=password_reenter_setup.text
            editor.putString("pass_lock",pass.toString())
            editor.putString("user_password","1")
            editor.apply()
            editor.commit()
            val intent= Intent(applicationContext, Home::class.java)
            startActivity(intent)
            finish()
        }

    }

}
