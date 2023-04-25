package com.example.comffee.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.comffee.R
import com.example.comffee.controllers.CurrentUser
import com.example.comffee.controllers.LoginController

class Login : AppCompatActivity() {
    private lateinit var btn_login: Button
    private lateinit var btn_regis: Button
    private lateinit var email: EditText
    private lateinit var pass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        // Inisiasi
        email = findViewById(R.id.in_email)
        pass = findViewById(R.id.in_password)

        //Register
        btn_regis = findViewById(R.id.btn_regis)
        btn_regis.setOnClickListener{
            val intent = Intent(this@Login, Register::class.java)
        }

        //Login
        btn_login = findViewById(R.id.btn_login)

        btn_login.setOnClickListener {
            val inEmail: String = email.text.toString()
            val inPass: String = pass.text.toString()

            val control = LoginController()
            val successLogin: Boolean = control.getLoginData(inEmail, inPass)

            if (successLogin) {
                Toast.makeText(applicationContext, "Selamat Datang!", Toast.LENGTH_SHORT).show()
                navigateToHomeScreen()
            } else {
                Toast.makeText(applicationContext, "Akun Tidak Ada!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToHomeScreen(){
        finish()
        lateinit var intent: Intent
        if (CurrentUser.getType() == "MEMBER") {
            intent = Intent(this@Login, Homepage::class.java)
        } else if (CurrentUser.getType() == "ADMIN") {
            intent = Intent(this@Login, AdminActivity::class.java)
        } else if (CurrentUser.getType() == "CASHIER") {
            intent = Intent(this@Login, CashierActivity::class.java)
        }
        startActivity(intent)
    }
}