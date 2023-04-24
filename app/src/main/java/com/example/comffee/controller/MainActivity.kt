package com.example.comffee.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.comffee.R

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mFragmentManager = supportFragmentManager
        val mWelcomeFragment = WelcomePage()
        val fragment = mFragmentManager.findFragmentByTag(WelcomePage::class.java.simpleName)

        if (fragment !is WelcomePage) {
            Log.d("MyFlexibleFragment", "Fragment Name :" + WelcomePage::class.java.simpleName)
            mFragmentManager.beginTransaction()
                .add(R.id.frame_container, mWelcomeFragment, WelcomePage::class.java.simpleName)
                .commit()
        }
    }
}