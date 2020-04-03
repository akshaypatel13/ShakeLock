package com.example.myapplication

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import com.example.shakelock.MyEncrypter
import com.kotlinpermissions.KotlinPermissions
import kotlinx.android.synthetic.main.activity_dashboard.*
import org.apache.commons.io.IOUtils
import java.io.*
import java.util.concurrent.Executors


class Dashboard : AppCompatActivity() {

    lateinit var myDir:File

    companion object{
        private val key = "I4p2qn2SnvLirArz"
        private val specString = "o5nOWzvsJvfR0b6g"
    }

    fun stringToWords(s : String) = s.trim().splitToSequence(',')
        .filter { it.isNotEmpty() } // or: .filter { it.isNotBlank() }
        .toList()

    private lateinit var mShakeDetector:ShakeDetector
    private lateinit var mSensorManager: SensorManager
    private lateinit var mAccelerometer: Sensor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KotlinPermissions.with(this) // where this is an FragmentActivity instance
            .permissions(
                Manifest.permission.USE_BIOMETRIC,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .onAccepted { permissions ->
            }
            .onDenied { permissions ->
                //List of denied permissions
            }
            .onForeverDenied { permissions ->
                //List of forever denied permissions
            }
            .ask()
        setContentView(R.layout.activity_dashboard)

        try {
            var inp_st: InputStream = assets.open("app3.gif")
            var bts: ByteArray = IOUtils.toByteArray(inp_st)
            giff.setBytes(bts)
            giff.startAnimation()
        } catch (exc: IOException) {
        }


        var savePath = "/storage/emulated/0/Encrypted"
        myDir = File(savePath)
        if(!myDir.exists()){
            myDir.mkdirs()
        }

        //Shake

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mShakeDetector = ShakeDetector(object: ShakeDetector.OnShakeListener {
            override fun onShake() {
                Log.d("Shaken","shaken")
//////////////////////////////////////////////////////////////////////////////////

                val check: SharedPreferences = getSharedPreferences("final_uris", 0)
                var ur = check.getString("urri", null)
                if (ur != null) {
                    ur = ur.substring(2,ur.length-2)
                }

                var urls = ur?.let { stringToWords(it) }
                var iter = urls?.iterator()
                var hs = HashSet<String>()
                var path = "/storage/emulated/0/"

                if(iter!=null) {
                    while (iter?.hasNext()!!) {
                        var demo = iter.next()
                        demo = demo.replace(" ", "")
                        //            var test = demo.substring(demo.indexOf("%3A"))
                        //            demo = URLDecoder.decode(test)
                        var fullPath = path + demo
                        Log.i("SP_FP", fullPath)
                        hs.add(fullPath)
                    }
                }

                encrypt(hs, savePath)
////////////////////////////////////////////////////////////////////////////////////////


                //  Toast.makeText(applicationContext,ur,Toast.LENGTH_LONG).show()
//                var json: String? = sp.getString("SHakeDetectTAG", null)
                //              val type: Type = object : TypeToken<ArrayList<String?>?>() {}.type
                //var fileList = gson.fromJson<>(json,type)
                //            Log.d("json sp",sp.toString())
            }
        })

        //---------------SharedPreferences----------------

        val check_user: SharedPreferences = this.getSharedPreferences("user_log", 0)
        var user_inp = check_user.getString("user_pattern", "0")
        var user_pin_inp = check_user.getString("user_pin", "0")
        var user_password_inp = check_user.getString("user_password", "0")
        //------------------Pattern-----------------------

        btn_pattern.setOnClickListener {

            if (user_inp.equals("0")) {

                val intent = Intent(this, SetupPattern::class.java)
                startActivity(intent)
            } else if (user_inp.equals("1")) {

                val intent = Intent(this, InputPattern::class.java)
                startActivity(intent)
            }

        }

        //Firebase
        btn_rst_pass.setOnClickListener {
            val i = Intent(applicationContext, SetSecurityKey::class.java)
            startActivity(i)
        }


        //checkshared
        val content: SharedPreferences = this.getSharedPreferences("final_uris", 0)
        var files = content.getString("urri", "0")
        if(files!!.isEmpty()){
            Toast.makeText(applicationContext,"No files Added",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(applicationContext,files,Toast.LENGTH_LONG).show()

        }
        //------------------FingerPrint------------------------

        val exec = Executors.newSingleThreadExecutor()
        val current_act: FragmentActivity = this

        val finger_prompt =
            BiometricPrompt(current_act, exec, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    val intent = Intent(applicationContext, Home::class.java)
                    startActivity(intent)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }
            })

        val info = BiometricPrompt.PromptInfo.Builder()
            .setNegativeButtonText("Cancel")
            .setDescription("Touch the fingerprint sensor.")
            .setTitle("FingerPrint")
            .build()

        btn_finger.setOnClickListener {

            finger_prompt.authenticate(info)
        }


        //---------------------Pin---------------------

        btn_pin.setOnClickListener {
            if (user_pin_inp.equals("0")) {
                val intent = Intent(applicationContext, SetupPin::class.java)
                startActivity(intent)
            } else if (user_pin_inp.equals("1")) {
                val intent = Intent(applicationContext, InputPin::class.java)
                startActivity(intent)
            }

        }


        //--------------------Password------------------


        btn_password.setOnClickListener {
            if (user_password_inp.equals("0")) {
                val intent = Intent(applicationContext, SetupPassword::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(applicationContext, InputPassword::class.java)
                startActivity(intent)
            }
        }

        //HelpandDoc
        btn_help.setOnClickListener {
            val i = Intent(applicationContext, HelpAndDocumentation::class.java)
            startActivity(i)
        }

    }
    override fun onBackPressed() {

    }

    protected override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI)
    }
    protected override fun onPause() {
        mSensorManager.unregisterListener(mShakeDetector)
        super.onPause()
    }


    fun encrypt(hs: HashSet<String>, savePath: String) {
        var Fpath = hs.iterator()
        while (Fpath.hasNext()) {
            var temp = Fpath.next()
            var fileName = temp.substring(temp.lastIndexOf("/") + 1)
            Log.i("SP_fileName", fileName)
            Log.i("SP_fileName", fileName)
            Log.i("SP_temp", temp)
            Log.i("Hash", hs.toString())
            // /storage/emulated/0/Download/L0_MobileExperience (1).pdf
            // file_name = L0_MobileExperience (1).pdf
            try {
                val encFile = File(temp)
                val input = FileInputStream(encFile)
                val outputFileEnc = File(savePath, fileName) // create empty file
                MyEncrypter.encryptToFile(
                    key,
                    specString, input, FileOutputStream(outputFileEnc)
                )
                Toast.makeText(applicationContext, "Encrypted", Toast.LENGTH_SHORT).show()
                encFile.delete()
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Unable to encrypt", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

}
