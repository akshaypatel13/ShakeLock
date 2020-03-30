package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_help_and_documentation.*

class HelpAndDocumentation : AppCompatActivity() {

    var relativeLayout: ConstraintLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_help_and_documentation)


        var adapter = ExpandableTextViewAdapter(this)
        eTV.setAdapter(adapter)
    }
}
