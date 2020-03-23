package com.mobilecomputing.shakelock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_fourth.*

class FourthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)

        configureButton()
    }

    private fun configureButton(){
        button4.setOnClickListener(){
            val int = Intent(this, FifthActivity::class.java)
            startActivity(int)
        }

    }
}
