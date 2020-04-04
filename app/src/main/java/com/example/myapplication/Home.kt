package com.example.myapplication
import kotlinx.android.synthetic.main.activity_home.*
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.net.toUri
import com.example.shakelock.MyEncrypter
import com.github.tbouron.shakedetector.library.ShakeDetector
import com.google.gson.Gson
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class Home : AppCompatActivity() {

    lateinit var myDir:File

    companion object{
        private val key = "I4p2qn2SnvLirArz"
        private val specString = "o5nOWzvsJvfR0b6g"
    }
    var uris: ArrayList<Uri>? = null
    var txt_pathShow: TextView? = null
    var btn_filePicker: Button? = null
    var btn_dec: Button? = null
    var myFileIntent: Intent? = null

    fun stringToWords(s : String) = s.trim().splitToSequence(',')
        .filter { it.isNotEmpty() } // or: .filter { it.isNotBlank() }
        .toList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var hs = HashSet<String>()
        var path = "/storage/emulated/0/"


        var savePath = "/storage/emulated/0/Encrypted"
        myDir = File(savePath)
        if(!myDir.exists()){
            myDir.mkdirs()
        }



        txt_pathShow = findViewById<View>(R.id.txt_path) as TextView
        btn_filePicker = findViewById<View>(R.id.btn_filePicker) as Button
        btn_dec = findViewById<View>(R.id.btn_dec) as Button
        btn_filePicker!!.setOnClickListener {
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)
                .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)




        }

        btn_dec!!.setOnClickListener {
            val check: SharedPreferences = this.getSharedPreferences("final_uris", 0)
            var ur = check.getString("urri", null)
            if(ur!=null){
                if (ur != null) {
                    ur = ur.substring(2,ur.length-2)
                }
                Log.i("SP",ur)
                var urls = ur?.let { stringToWords(it) }
                Log.i("SP_123", urls?.toString())
                var iter = urls?.iterator()


                while (iter?.hasNext()!!)
                {
                    var demo = iter.next()
                    demo = demo.replace(" ","")
                    //            var test = demo.substring(demo.indexOf("%3A"))
                    //            demo = URLDecoder.decode(test)
                    var fullPath = path+demo
                    Log.i("SP_FP", fullPath)
                    hs.add(fullPath)
                }
                Log.d("checking", hs.toString())
                Log.d("checking", savePath)
                decrypt(hs, savePath)
            }else{
                Toast.makeText(applicationContext, "No Files selected", Toast.LENGTH_SHORT).show()
            }

        }

        btn_enc.setOnClickListener{
            val check: SharedPreferences = this.getSharedPreferences("final_uris", 0)
            var ur = check.getString("urri", null)
            if(ur!=null){
                if (ur != null) {
                    ur = ur!!.substring(2, ur!!.length-2)
                }
                Log.i("SP",ur)
                var urls = ur?.let { stringToWords(it) }
                Log.i("SP_123", urls?.toString())
                var iter = urls?.iterator()


                while (iter?.hasNext()!!)
                {
                    var demo = iter.next()
                    demo = demo.replace(" ","")
                    //            var test = demo.substring(demo.indexOf("%3A"))
                    //            demo = URLDecoder.decode(test)
                    var fullPath = path+demo
                    Log.i("SP_FP", fullPath)
                    hs.add(fullPath)
                }
                Log.d("checking", hs.toString())
                Log.d("checking", savePath)
                encrypt(hs, savePath)
            }else{
                Toast.makeText(applicationContext, "No Files selected", Toast.LENGTH_SHORT).show()
            }
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
                        var demo = imageUri.getPath()?.toUri().toString()
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



    private fun add_to_List(list: ArrayList<Uri>) {

        uris=list
        val check: SharedPreferences =this.getSharedPreferences("final_uris",0)
        val editor:SharedPreferences.Editor=check.edit()
        val gson = Gson()
        val json: String = gson.toJson(uris.toString())
        editor.putString("urri", json)
        editor.commit()
        editor.apply()


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

    fun encrypt(hs: HashSet<String>, savePath: String) {
        var Fpath = hs.iterator()
        if(Fpath!=null){
            while (Fpath.hasNext()) {
                var temp = Fpath.next()
                var fileName = temp.substring(temp.lastIndexOf("/") + 1)
                Log.i("SP_fileName", fileName)
                Log.i("SP_temp", temp)
                Log.i("Hash", hs.toString())
                // /storage/emulated/0/Download/L0_MobileExperience (1).pdf
                // file_name = L0_MobileExperience (1).pdf
                try {
                    val encFile = File(temp)
                    val input = FileInputStream(encFile)
                    val outputFileEnc = File(savePath, fileName) // create empty file
                    MyEncrypter.encryptToFile(key, specString, input, FileOutputStream(outputFileEnc))
                    Toast.makeText(applicationContext, "Encrypted", Toast.LENGTH_SHORT).show()
                    encFile.delete()
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, "Unable to encrypt", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
        }
        else{
            Toast.makeText(applicationContext, "No Files selected", Toast.LENGTH_SHORT).show()
        }

    }

    fun decrypt(hs: HashSet<String>, savePath: String){
        var Fpath = hs.iterator()

        while (Fpath.hasNext()) {
            var temp = Fpath.next()
            var fileName = temp.substring(temp.lastIndexOf("/") + 1)
            // /storage/emulated/0/Download/L0_MobileExperience (1).pdf
            // file_name = L0_MobileExperience (1).pdf
            try {

                val outputFileDec = File(temp)//empty file dec
                val encFile = File(savePath, fileName)//get enc file

                MyEncrypter.decryptToFile(
                    key,
                    specString,
                    FileInputStream(encFile),
                    FileOutputStream(outputFileDec)
                )
                //set for image view
                //imageView.setImageURI(Uri.fromFile(outputFileDec))
                //if you want to delete drawable decrypted delete below line
                encFile.delete()
                Toast.makeText(applicationContext, "decryted", Toast.LENGTH_SHORT).show()
                val shared_delete=this.getSharedPreferences("final_uris",0)
                shared_delete.edit().remove("urri").commit();
                txt_pathShow!!.text = ""
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Unable to decrypt", Toast.LENGTH_SHORT)
                    .show()
                e.printStackTrace()
            }
        }
        hs.clear()
    }



}