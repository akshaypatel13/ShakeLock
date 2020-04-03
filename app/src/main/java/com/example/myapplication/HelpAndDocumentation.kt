package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_help_and_documentation.*
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.io.InputStream

class HelpAndDocumentation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_help_and_documentation)

        try{
            var inp_st: InputStream =assets.open("app.gif")
            var bts:ByteArray= IOUtils.toByteArray(inp_st)
            gifI.setBytes(bts)
            gifI.startAnimation()
        }catch(exc: IOException){
        }

        var adapter = ExpandableTextViewAdapter(this)
        eTV.setAdapter(adapter)

        contact.setOnClickListener {
            val i= Intent(applicationContext,ContactUs::class.java)
            startActivity(i)
        }

    }
}
