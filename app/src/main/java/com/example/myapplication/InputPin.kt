package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_input_pin.*
import com.andrognito.pinlockview.PinLockListener
import kotlinx.android.synthetic.main.activity_input_pattern.*
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.io.InputStream

class InputPin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_pin)

        try{
            var inp_st: InputStream =assets.open("app.gif")
            var bts:ByteArray= IOUtils.toByteArray(inp_st)
            gifImage3.setBytes(bts)
            gifImage3.startAnimation()
        }catch(exc: IOException){
        }

        Toast.makeText(applicationContext,"Input",Toast.LENGTH_SHORT).show()
        pin_input.attachIndicatorDots(ind_dots_input)
        pin_input.setPinLockListener(object :PinLockListener{
            override fun onEmpty() {
            }

            override fun onComplete(pin: String?) {
                val check_user: SharedPreferences =getSharedPreferences("user_log",0)
                var pinuser=check_user.getString("pin_lock","0")
                if(pin.equals(pinuser)){
                    val intent= Intent(applicationContext, Home::class.java)
                    startActivity(intent)
                }
            }

            override fun onPinChange(pinLength: Int, intermediatePin: String?) {
            }

        })
    }

}
