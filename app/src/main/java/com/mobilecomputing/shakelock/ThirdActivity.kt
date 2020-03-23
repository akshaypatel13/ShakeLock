package com.mobilecomputing.shakelock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_third.*

class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        configureButton()
    }

    private fun configureButton(){
        button3.setOnClickListener(){
            val int = Intent(this, FourthActivity::class.java)
            startActivity(int)
        }

    }
}
