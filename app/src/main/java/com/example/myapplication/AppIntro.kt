package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ramotion.paperonboarding.PaperOnboardingFragment
import com.ramotion.paperonboarding.PaperOnboardingPage
import kotlinx.android.synthetic.main.activity_app_intro.*


class AppIntro : AppCompatActivity() {
    private val fm = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_intro)

        val scr1 = PaperOnboardingPage(
            "Hotels",
            "All hotels and hostels are sorted by hospitality rating",
            Color.parseColor("#678FB4"), R.drawable.icon_file_doc, R.drawable.dot_empty
        )
        val scr2 = PaperOnboardingPage(
            "Banks",
            "We carefully verify all banks before add them into the app",
            Color.parseColor("#65B0B4"), R.drawable.ic_play_icon, R.drawable.icon_file_pdf
        )
        val scr3 = PaperOnboardingPage(
            "Stores",
            "All local stores are categorized for your convenience",
            Color.parseColor("#9B90BC"), R.drawable.icon_file_ppt, R.drawable.icon_file_xls
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
}
