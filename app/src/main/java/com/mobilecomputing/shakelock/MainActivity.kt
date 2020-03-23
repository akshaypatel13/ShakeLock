package com.mobilecomputing.shakelock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureButton()
    }

    private fun configureButton(){
        button.setOnClickListener(){
            val int = Intent(this, SecondActivity::class.java)
            startActivity(int)
            }

        }




}
