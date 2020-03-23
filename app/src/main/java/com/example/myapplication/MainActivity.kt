package com.example.shakelock

import android.Manifest
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.shakelock.MyEncrypter
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {

    lateinit var myDir:File

    companion object{
        private val FILE_NAME_ENC = "L1_MobileExperience.pdf"
        private val FILE_NAME_ENC_2 = "L0_MobileExperience.pdf"
        private val FILE_NAME_DEC = "L0_MobileExperience.pdf"
        private val key = "I4p2qn2SnvLirArz"
        private val specString = "o5nOWzvsJvfR0b6g"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Dexter.withActivity(this)
                .withPermissions(*arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                )
                .withListener(object: MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        btn_dec.isEnabled = true
                        btn_enc.isEnabled = true
                    }

                    override fun onPermissionRationaleShouldBeShown(
                            permissions: MutableList<PermissionRequest>?,
                            token: PermissionToken?
                    ) {
                        Toast.makeText(this@MainActivity, "Yo should accept permissions",Toast.LENGTH_SHORT).show()

                    }

                }).check();

        val root = Environment.getExternalStorageDirectory().toString()
        myDir = File("$root/saved_images")
        if(!myDir.exists())
            myDir.mkdirs()
        btn_enc.setOnClickListener{
            //convert drawable to bitmap
            val encFile=File(myDir, FILE_NAME_DEC)
            val input = FileInputStream(encFile)
            val outputFileEnc = File(myDir, FILE_NAME_ENC) // create empty file
            try{
                MyEncrypter.encryptToFile(key, specString, input,FileOutputStream(outputFileEnc))
                Toast.makeText( this@MainActivity,"Encrypted",Toast.LENGTH_SHORT).show()
                encFile.delete()
            }
            catch(e:Exception)
            {
                e.printStackTrace()
            }
        }

        btn_dec.setOnClickListener{
            val outputFileDec=File(myDir, FILE_NAME_ENC_2)//empty file dec
            val encFile=File(myDir, FILE_NAME_ENC)//get enc file
            try {
                MyEncrypter.decryptToFile(key,specString, FileInputStream(encFile),FileOutputStream(outputFileDec))
                //set for image view
                //imageView.setImageURI(Uri.fromFile(outputFileDec))
                //if you want to delete drawable decrypted delete below line
                encFile.delete()
                Toast.makeText(this@MainActivity,"decryted",Toast.LENGTH_SHORT).show()
            }
            catch(e:Exception)
            {
                e.printStackTrace()
            }
        }

    }
}

