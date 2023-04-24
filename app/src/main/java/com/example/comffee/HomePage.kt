package com.example.comffee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HomePage : AppCompatActivity() {
    companion object {
        const val  EXTRA_EMAIL = "extra_age"
        const val  EXTRA_PASSWORD = "extra_name"
        const val EXTRA_USER = "extra_person"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
    }
}