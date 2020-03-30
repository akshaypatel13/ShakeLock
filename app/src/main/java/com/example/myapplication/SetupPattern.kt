package com.example.myapplication


import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener
import kotlinx.android.synthetic.main.activity_setup_pattern.*
import com.andrognito.patternlockview.utils.PatternLockUtils

class SetupPattern : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup_pattern)

        pattern_lock_screen.addPatternLockListener(object :PatternLockViewListener{
            override fun onComplete(pattern: MutableList<PatternLockView.Dot>?) {
                val check_user:SharedPreferences=getSharedPreferences("user_log",0)
                val editor:SharedPreferences.Editor=check_user.edit()
                val pat=PatternLockUtils.patternToString(pattern_lock_screen,pattern)
                editor.putString("pattern_lock",pat)
                editor.putString("user_pattern","1")
                editor.apply()
                editor.commit()
                val intent= Intent(applicationContext, Home::class.java)
                startActivity(intent)
                finish()

            }

            override fun onCleared() {

            }

            override fun onStarted() {

            }

            override fun onProgress(progressPattern: MutableList<PatternLockView.Dot>?) {

            }

        })
    }
}
