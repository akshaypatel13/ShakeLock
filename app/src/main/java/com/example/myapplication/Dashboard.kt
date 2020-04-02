package com.example.myapplication

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.biometric.BiometricPrompt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import com.kotlinpermissions.KotlinPermissions
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_main.gifImageView
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.Executors

class Dashboard : AppCompatActivity() {

    private lateinit var mShakeDetector: ShakeDetector
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
            var inp_st: InputStream = assets.open("gif1.gif")
            var bts: ByteArray = IOUtils.toByteArray(inp_st)
            gifImageView.setBytes(bts)
            gifImageView.startAnimation()
        } catch (exc: IOException) {
        }

        //---------------SharedPreferences----------------

        val check_user: SharedPreferences = this.getSharedPreferences("user_log", 0)
        var user_inp = check_user.getString("user_pattern", "0")
        var user_pin_inp = check_user.getString("user_pin", "0")
        var user_password_inp = check_user.getString("user_password", "0")
        var set_secirity_inp = check_user.getString("user_security", "0")
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
            .setNegativeButtonText("a")
            .setDescription("aa")
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

        //--------------------Set Security Key------------------
        set_security_key.setOnClickListener {
            val intent = Intent(applicationContext, SetSecurityKey::class.java)
            startActivity(intent)
        }

        // Shake Detector
        //
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mShakeDetector = ShakeDetector(object : ShakeDetector.OnShakeListener {
            override fun onShake() {
                Log.d("Shaken", "check reached")
                val check: SharedPreferences = getSharedPreferences("shared_uris", 0)
                val gson1 = Gson()
                var ur = check.getString("urri", null)
//                Log.i("aa", ur)

                Toast.makeText(applicationContext, ur, Toast.LENGTH_LONG).show()
                //  Toast.makeText(applicationContext,ur,Toast.LENGTH_LONG).show()
//                var json: String? = sp.getString("SHakeDetectTAG", null)
                //              val type: Type = object : TypeToken<ArrayList<String?>?>() {}.type
                //var fileList = gson.fromJson<>(json,type)
                //            Log.d("json sp",sp.toString())
            }
        })
        //
        //--


    }

    override fun onBackPressed() {

    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI)
    }
    override fun onPause() {
        mSensorManager.unregisterListener(mShakeDetector)
        super.onPause()
    }

}


