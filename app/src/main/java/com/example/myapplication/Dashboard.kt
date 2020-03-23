package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import androidx.biometric.BiometricPrompt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_main.gifImageView
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.Executors

class Dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        try{
            var inp_st: InputStream =assets.open("pass.gif")
            var bts:ByteArray= IOUtils.toByteArray(inp_st)
            gifImageView.setBytes(bts)
            gifImageView.startAnimation()
        }catch(exc: IOException){
        }

        //---------------SharedPreferences----------------

        val check_user:SharedPreferences=this.getSharedPreferences("user_log",0)
        var user_inp=check_user.getString("user_pattern","0")
        var user_pin_inp=check_user.getString("user_pin","0")
        var user_password_inp=check_user.getString("user_password","0")
        //------------------Pattern-----------------------

        btn_pattern.setOnClickListener {

            if(user_inp.equals("0")){

                val intent=Intent(this, SetupPattern::class.java)
                startActivity(intent)
            }else if(user_inp.equals("1")) {

                val intent=Intent(this, InputPattern::class.java)
                startActivity(intent)
            }

        }


        //------------------FingerPrint------------------------

        val exec = Executors.newSingleThreadExecutor()
        val current_act:FragmentActivity=this

        val finger_prompt=BiometricPrompt(current_act,exec,object :BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                val intent=Intent(applicationContext, Home::class.java)
                startActivity(intent)
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
            }
        })


        val info=BiometricPrompt.PromptInfo.Builder()
            .setNegativeButtonText("a")
            .setDescription("aa")
            .setTitle("FingerPrint")
            .build()

        btn_finger.setOnClickListener {
            finger_prompt.authenticate(info)
        }


        //---------------------Pin---------------------

        btn_pin.setOnClickListener {
            if(user_pin_inp.equals("0")){
                val intent=Intent(applicationContext, SetupPin::class.java)
                startActivity(intent)
            }
            else if(user_pin_inp.equals("1")){
                val intent=Intent(applicationContext, InputPin::class.java)
                startActivity(intent)
            }

        }


        //--------------------Password------------------


        btn_password.setOnClickListener {
            if(user_password_inp.equals("0")) {
                val intent = Intent(applicationContext, SetupPassword::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(applicationContext, InputPassword::class.java)
                startActivity(intent)
            }
        }



    }
}
