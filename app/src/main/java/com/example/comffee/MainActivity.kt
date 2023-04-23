package com.example.comffee

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val topBar = findViewById<MaterialToolbar>(R.id.TopBarApp)

        topBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_menu -> {
                    setContentView(R.layout.activity_main)
                    return@setOnMenuItemClickListener true
                }
                R.id.product_menu -> {
                    setContentView(R.layout.activity_order_history)
                    return@setOnMenuItemClickListener true
                }
                else -> return@setOnMenuItemClickListener false
            }

        }
    }
}