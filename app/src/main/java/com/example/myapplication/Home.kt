package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Button
import androidx.annotation.RequiresApi
import com.example.myapplication.R

class Home : AppCompatActivity() {

    val PICKFILE_RESULT_CODE = 111
    val fileUris = ArrayList<Uri>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val fileBtn = findViewById<Button>(R.id.fileBtn)
        val fileUris = ArrayList<String>()
        fileBtn.setOnClickListener {

            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(
                Intent.createChooser(intent, "Select a file"),
                PICKFILE_RESULT_CODE
            )

        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICKFILE_RESULT_CODE -> if (resultCode === Activity.RESULT_OK) {
                if (null != data) {
                    if (null !=data.clipData) {
                        for (i in 0 until data.clipData!!.itemCount) {
                            val uri = data.clipData!!.getItemAt(i).uri
                            fileUris.add(uri)
                        }
                    } else {
                        val uri = data.data
                        if (uri != null) {
                            fileUris.add(uri)
                        }
                    }
                }

            }
        }
    }
}
