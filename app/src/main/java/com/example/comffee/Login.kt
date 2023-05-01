package com.example.comffee

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.ProgressBar
import android.widget.Toast
import com.example.comffee.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    private lateinit var email: String
    private lateinit var pass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging in")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebase
        auth = FirebaseAuth.getInstance()

        binding.btnRegis.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            email = binding.email.text.toString()
            pass = binding.pass.text.toString()

            //validasi email kosong
            if (email.isEmpty()){
                binding.email.error = "email harus diisi!"
                binding.email.requestFocus()
                return@setOnClickListener
            }

            //validasi apakah input adalah email
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.email.error = "email tidak valid!"
                binding.email.requestFocus()
                return@setOnClickListener
            }

            //validasi password kosong
            if (pass.isEmpty()){
                binding.pass.error = "password harus diisi!"
                binding.pass.requestFocus()
                return@setOnClickListener
            }

            //validasi password terlalu pendek
            if (pass.length < 8){
                binding.pass.error = "password terlalu pendek!"
                binding.pass.requestFocus()
                return@setOnClickListener
            }

            //lolos cek validasi
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
//        progressDialog.show()
        auth.signInWithEmailAndPassword(email, pass)
            .addOnSuccessListener {
                //if login success
//                progressDialog.dismiss()
                Toast.makeText(this, "Successfully Logged in", Toast.LENGTH_SHORT).show()

                //open main activity
                val intent = Intent(this, Homepage::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
//                if login failed
                progressDialog.dismiss()

                Toast.makeText(this, "Login failled. $it", Toast.LENGTH_LONG).show()
            }
    }
}