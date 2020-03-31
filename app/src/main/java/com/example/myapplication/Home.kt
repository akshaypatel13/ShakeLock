package com.example.myapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import co.csadev.kwikpicker.KwikPicker
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.*
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {
    var uris: ArrayList<Uri>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        pick.setOnClickListener {
            val kwikPicker = KwikPicker.Builder(applicationContext,
                imageProvider = { imageView, uri ->
                    Glide.with(this)
                        .load(uri)
                        .into(imageView)
                },
                onMultiImageSelectedListener = { list: ArrayList<Uri> ->
                    add_to_List(list)
                },
                peekHeight = 1600,
                showTitle = false,
                completeButtonText = "Done",
                emptySelectionText = "No Selection")
                .create(applicationContext)
            kwikPicker.show(supportFragmentManager)

        }



    }

    private fun add_to_List(list: ArrayList<Uri>) {

        uris=list
        Toast.makeText(applicationContext,uris.toString(),Toast.LENGTH_LONG).show()

    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext,Dashboard::class.java)
        startActivity(intent)
    }

}
