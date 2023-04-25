package com.example.comffee.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.comffee.R
import com.example.comffee.controllers.RegisterControllers
import com.example.comffee.model.User
import com.google.android.material.bottomnavigation.BottomNavigationView

class Register : AppCompatActivity() {
    private lateinit var usernameET: EditText
    private lateinit var addressET: EditText
    private lateinit var emailET: EditText
    private lateinit var passwordET: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        supportActionBar?.hide()

        val loginButton:Button = findViewById(R.id.btn_login)
        loginButton.setOnClickListener {
            val intent = Intent(this@Register, Login::class.java)
            startActivity(intent)
        }

        usernameET = findViewById(R.id.in_username)
        emailET = findViewById(R.id.in_email)
        passwordET = findViewById(R.id.in_password)
        addressET = findViewById(R.id.in_address)
        registerButton = findViewById(R.id.btn_regis)

        registerButton.setOnClickListener {
            val control = RegisterControllers()
            val user = User(0, usernameET.text.toString(), emailET.text.toString(), passwordET.text.toString(),addressET.text.toString())


            val isRegistered = control.registerUser(user)

            if (isRegistered) {
                Toast.makeText(applicationContext, "Registered, Please Login", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@Register, Login::class.java)
                startActivity(intent)
            } else {
                    Toast.makeText(applicationContext, "Password Mismatch!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}