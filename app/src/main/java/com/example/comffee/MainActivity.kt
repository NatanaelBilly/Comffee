package com.example.comffee

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(){
    @SuppressLint("MissingInflatedId")
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