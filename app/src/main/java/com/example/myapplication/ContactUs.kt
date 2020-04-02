package com.example.myapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_contact_us.*
import java.lang.Exception

class ContactUs : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)

        sendEmailButton.setOnClickListener{

            var recepient = recipientEId.text.toString().trim()
            var message = message.text.toString().trim()
            var subject = subjectLine.text.toString().trim()

            sendEmail(recepient,message,subject)
        }
    }

    //references : https://www.youtube.com/watch?v=9y25slI68S8
    private fun sendEmail(recepient: String, message: String, subject: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:")
        intent.type = "text/plain"

        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recepient))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)

        try {
            startActivity(Intent.createChooser(intent,"Choose Email Client..."))
        }
        catch (e: Exception){
            Toast.makeText(this,e.message, Toast.LENGTH_LONG).show()
        }

    }
}
