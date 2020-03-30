package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_input_password.*
import kotlinx.android.synthetic.main.activity_setup_password.submit

class InputPassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_password)




        password_reenter_input.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                var pass_first=password_input_first.text.toString()
                var pass_second=password_reenter_input.text.toString()
                if(pass_second.isNotEmpty()){
                if(pass_second!!.equals(pass_first)){
                    reenter_password_input.error=""
                }else{
                    reenter_password_input.error="not match"
                }}
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })



        submit.setOnClickListener {
            val check_user: SharedPreferences =getSharedPreferences("user_log",0)

            val pass=check_user.getString("pass_lock","0")
            var pass_check=password_reenter_input.text.toString()
            if(pass.equals(pass_check)) {
                val intent = Intent(applicationContext, Home::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(applicationContext,"Wrong Password",Toast.LENGTH_SHORT).show()
            }
        }

    }
}
