package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_input_pattern.*
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.listener.PatternLockViewListener
import com.andrognito.patternlockview.utils.PatternLockUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.io.InputStream

class InputPattern : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_pattern)
        Toast.makeText(applicationContext,"Input",Toast.LENGTH_LONG).show()

        try{
            var inp_st: InputStream =assets.open("app.gif")
            var bts:ByteArray= IOUtils.toByteArray(inp_st)
            gifImage2.setBytes(bts)
            gifImage2.startAnimation()
        }catch(exc: IOException){
        }

        pattern_input.addPatternLockListener(object :PatternLockViewListener{
            override fun onComplete(pattern: MutableList<PatternLockView.Dot>?) {
                val check_user: SharedPreferences =getSharedPreferences("user_log",0)
                var pat_shared=check_user.getString("pattern_lock","0")
                val editor:SharedPreferences.Editor=check_user.edit()
                editor.apply()
                editor.commit()
                if(pat_shared.equals(PatternLockUtils.patternToString(pattern_input,pattern))){
                    val intent= Intent(applicationContext, Home::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(applicationContext,"Wrong Pattern",Toast.LENGTH_SHORT).show()
                    pattern_input.clearPattern()
                }
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
