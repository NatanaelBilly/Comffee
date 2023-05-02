package com.example.comffee

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.comffee.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private lateinit var auth: FirebaseAuth

    // init firestore
    val firestore = Firebase.firestore

    private lateinit var progressDialog: ProgressDialog


    private lateinit var username :String
    private lateinit var email: String
    private lateinit var pass: String
    private lateinit var address: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = binding.username.text.toString()
        email = binding.email.text.toString()
        pass = binding.pass.text.toString()
        address = binding.address.text.toString()

        //init firebase
        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        //check box click checker
        binding.chkDaftar.setOnCheckedChangeListener { _, isChecked ->
            binding.btnRegis.isEnabled = isChecked
            binding.btnRegis.isClickable = isChecked
        }

        //set progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Creating an account")
        progressDialog.setCanceledOnTouchOutside(false)

        //click button daftar
        binding.btnRegis.setOnClickListener {

            username = binding.username.text.toString()
            email = binding.email.text.toString()
            pass = binding.pass.text.toString()
            address = binding.address.text.toString()

            //validasi username kosong
            if (username.isEmpty()){
                binding.username.error = "username harus diisi!"
                binding.username.requestFocus()
                return@setOnClickListener
            }

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

            //validasi email kosong
            if (address.isEmpty()){
                binding.address.error = "Alamat harus diisi!"
                binding.address.requestFocus()
                return@setOnClickListener
            }

            //lolos cek validasi
            firebaseRegister()
        }

    }

    private fun firebaseRegister() {
        email = binding.email.text.toString()
        pass = binding.pass.text.toString()

        //show progress dialog
        progressDialog.show()

        //create account
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnSuccessListener {
                //if register success
                progressDialog.dismiss()

                Toast.makeText(this, "Akun telah sukses dibuat", Toast.LENGTH_SHORT).show()

                //insert data to firestore
                insertData()

                //continue to activity
                val intent = Intent(this, Homepage::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            .addOnFailureListener{
                //if register failed
                progressDialog.dismiss()
                Toast.makeText(this, "Register gagal!. ${it.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun insertData() {
        email = binding.email.text.toString()
        username = binding.username.text.toString()

        // value yang mau ditambahkan ke db
        val user = hashMapOf(
            "username" to username,
            "email" to email,
            "pass" to pass,
            "address" to address,
            "type" to 1
        )

        //add data to db
        firestore.collection("users")
            .document(email).set(user)
            .addOnSuccessListener {
                println("Sukses! Dokumen telah ditambahkan ke firestore dengab email: $email")
            }
            .addOnFailureListener{
                println("Error")
            }
    }
}