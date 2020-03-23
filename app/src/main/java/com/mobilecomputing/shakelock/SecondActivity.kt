package com.mobilecomputing.shakelock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        configureButton()
    }

    private fun configureButton(){
        button2.setOnClickListener(){
            val int = Intent(this, ThirdActivity::class.java)
            startActivity(int)
        }

    }
}
