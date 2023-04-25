package com.example.comffee.view

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.comffee.R
import com.example.comffee.controller.ActiveUser
import com.example.comffee.controllers.LoginControllers
import com.example.comffee.controllers.RegisterControllers
import com.example.comffee.controllers.UserControllers

class Login : AppCompatActivity() {
    private lateinit var loginButton: Button
    private lateinit var inputEmailET: EditText
    private lateinit var inputPasswordET: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        supportActionBar?.hide()

        // Manual Login
        loginButton = findViewById(R.id.btn_login)
        inputEmailET = findViewById(R.id.in_email)
        inputPasswordET = findViewById(R.id.in_password)

        loginButton.setOnClickListener {
            val inputEmail: String = inputEmailET.text.toString()
            val inputPassword: String = inputPasswordET.text.toString()

            val control = LoginControllers()
            val successLogin: Boolean = control.getLoginData(inputEmail, inputPassword)

            if (successLogin){
                Toast.makeText(applicationContext, "Welcome!", Toast.LENGTH_SHORT).show()
                navigateToHomeScreen()
            } else {
                Toast.makeText(applicationContext, "No Account Found!", Toast.LENGTH_SHORT).show()
            }
        }

        // To Register
        signupTextSpan() // Partial clickable textview
    }

    fun signupTextSpan() {
        val signupText: TextView = findViewById(R.id.blm_pny_akun)
        val spannableString = SpannableString(signupText.text.toString())

        val clickableSpan = object: ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@Login, Register::class.java)
                startActivity(intent)
            }
        }

        spannableString.setSpan(clickableSpan, 25, 36, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        signupText.text = spannableString
        signupText.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun navigateToHomeScreen(){
        finish()
        lateinit var intent: Intent
        intent = Intent(this@Login, Home::class.java)
        startActivity(intent)
    }

//    private fun navigateToCompleteProfile() {
//        finish()
//        val intent = Intent(this@Login, CompleteProfile::class.java)
//        startActivity(intent)
//    }
}