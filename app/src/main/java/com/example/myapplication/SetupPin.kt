package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_setup_pin.*
import com.andrognito.pinlockview.PinLockListener

class SetupPin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup_pin)

        pin_setup.attachIndicatorDots(ind_dots_setup)

        pin_setup.setPinLockListener(object :PinLockListener{
            override fun onEmpty() {

            }

            override fun onComplete(pin: String?) {
                val check_user: SharedPreferences =getSharedPreferences("user_log",0)
                val editor: SharedPreferences.Editor=check_user.edit()
                editor.putString("pin_lock",pin)
                editor.putString("user_pin","1")
                editor.apply()
                editor.commit()
                val intent= Intent(applicationContext, Home::class.java)
                startActivity(intent)
                finish()
            }

            override fun onPinChange(pinLength: Int, intermediatePin: String?) {

            }

        })
    }
}
