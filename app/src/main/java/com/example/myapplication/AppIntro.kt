package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kotlinpermissions.KotlinPermissions
import com.ramotion.paperonboarding.PaperOnboardingFragment
import com.ramotion.paperonboarding.PaperOnboardingPage
import kotlinx.android.synthetic.main.activity_app_intro.*
import kotlinx.android.synthetic.main.activity_setup_password.*


class AppIntro : AppCompatActivity() {
    private val fm = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_intro)

        val check_app: SharedPreferences =getSharedPreferences("app_intro",0)
        val editor: SharedPreferences.Editor=check_app.edit()
        editor.putString("first","1")
        editor.apply()
        editor.commit()


        val scr1 = PaperOnboardingPage(
            "Locking Styles",
            "Choose one from different locking mechanisms: Pattern, Pin, Password, Biometric",
            Color.parseColor("#678FB4"), R.drawable.patt, R.drawable.dot_empty
        )
        val scr2 = PaperOnboardingPage(
            "Encrypt/Decrypt",
            "Hide/Unhide images stored in the local storage of the device.",
            Color.parseColor("#65B0B4"), R.drawable.enc, R.drawable.dot_empty
        )
        val scr3 = PaperOnboardingPage(
            "Help And Documentation",
            "Any information related to the working of this android is provided in Help section.",
            Color.parseColor("#9B90BC"), R.drawable.help, R.drawable.dot_empty
        )


        val elements: ArrayList<PaperOnboardingPage> = ArrayList()
        elements.add(scr1)
        elements.add(scr2)
        elements.add(scr3)

        val onBoardingFragment =
            PaperOnboardingFragment.newInstance(elements)
        val ft = fm.beginTransaction()

        onBoardingFragment.setOnRightOutListener {
            var i = Intent(this, Dashboard::class.java)
            startActivity(i)
            //Toast.makeText(baseContext, "last listners", Toast.LENGTH_LONG).show()
        }

        start.setOnClickListener {
            var i = Intent(this, Dashboard::class.java)
            startActivity(i)
        }

        ft.add(R.id.fragment, onBoardingFragment)
        ft.commit()
    }
    override fun onBackPressed() {

    }
}
