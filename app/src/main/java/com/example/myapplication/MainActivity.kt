package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.io.InputStream
import kotlinx.android.synthetic.main.activity_main.*


//https://www.youtube.com/watch?v=JLIFqqnSNmg



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        var slogan = findViewById<TextView>(R.id.slogan)
        var name = findViewById<TextView>(R.id.appname)
        val bot_anim = AnimationUtils.loadAnimation(this, R.anim.bottom)
        name.startAnimation(bot_anim)
        slogan.startAnimation(bot_anim)
        try {
            var inp_st: InputStream = assets.open("splash4.gif")
            var bts: ByteArray = IOUtils.toByteArray(inp_st)
            gifImageView.setBytes(bts)
            gifImageView.startAnimation()


        } catch (exc: IOException) {

        }
        Handler().postDelayed({
            val check_app: SharedPreferences =this.getSharedPreferences("app_intro",0)
            var first_app=check_app.getString("first","0")

            if(first_app.equals("0")) {
                val intent = Intent(this, AppIntro::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, Dashboard::class.java)
                startActivity(intent)
            }
        }, 2000)
    }
    override fun onBackPressed() {

    }
}