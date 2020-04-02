package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import co.csadev.kwikpicker.KwikPicker
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.*
import kotlinx.android.synthetic.main.activity_home.*
import com.github.tbouron.shakedetector.library.ShakeDetector

class Home : AppCompatActivity() {
    var uris: ArrayList<Uri>? = null
    var txt_pathShow: TextView? = null
    var btn_filePicker: Button? = null
    var myFileIntent: Intent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        ShakeDetector.create(
            this
        ) { Toast.makeText(applicationContext, "Device shaken!", Toast.LENGTH_SHORT).show()
        Log.d("test Shake", "shaker")}




        txt_pathShow = findViewById<View>(R.id.txt_path) as TextView
        btn_filePicker = findViewById<View>(R.id.btn_filePicker) as Button
        btn_filePicker!!.setOnClickListener {
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)
                .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("abb", data.toString())
        Log.d("abb", " ")
        Toast.makeText(applicationContext, data.toString(), Toast.LENGTH_LONG).show()

        val data2= ArrayList<Uri>()
        if (requestCode == 111) {
            if (resultCode == Activity.RESULT_OK) {
                //multiple
                if (data?.getClipData() != null) {

                    var count = data.getClipData()!!.getItemCount()
                    var currentItem = 0
                    while (currentItem < count) {
                        var imageUri: Uri = data.getClipData()!!.getItemAt(currentItem).getUri()
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        var demo = imageUri.getPath().toUri().toString()
                        demo = demo.substring(demo.indexOf(":") + 1)

                        data2.add(demo.toUri())
                        currentItem = currentItem + 1;
                        Log.d("abb", data2.toString())
                        add_to_List(data2)
                        txt_pathShow!!.text = data2.toString()
                    }
                //single
                } else if (data?.getData() != null) {

                    val FilePath = data.getData()!!.getPath()
                    var demo =FilePath?.toUri()!!.toString()
                    demo = demo.substring(demo.indexOf(":") + 1)

                    data2.add(demo.toUri())
                    Log.d("abb", data2.toString())
                    add_to_List(data2)
                    txt_pathShow!!.text = data2.toString()
                }

            }

        }
    }

        //btn_filePicker.setOnClickListener {
//            val kwikPicker = KwikPicker.Builder(applicationContext,
//                imageProvider = { imageView, uri ->
//                    Glide.with(this)
//                        .load(uri)
//                        .into(imageView)
//                },
//                onMultiImageSelectedListener = { list: ArrayList<Uri> ->
//                    add_to_List(list)
//                },
//                peekHeight = 1600,
//                showTitle = false,
//                completeButtonText = "Done",
//                emptySelectionText = "No Selection")
//                .create(applicationContext)
//            kwikPicker.show(supportFragmentManager)
//        }
//    }

    private fun add_to_List(list: ArrayList<Uri>) {

        uris=list
        Toast.makeText(applicationContext,uris.toString(),Toast.LENGTH_LONG).show()


    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext,Dashboard::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        ShakeDetector.start()
    }

    override fun onStop() {
        super.onStop()
        ShakeDetector.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        ShakeDetector.destroy()
    }

}
