package com.example.comffee

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.comffee.databinding.ActivityEditProfilBinding
import com.example.comffee.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class EditProfil : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfilBinding

    private val firestore = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser
    val userData = firestore.collection("users").document(currentUser?.email.toString())

    private lateinit var progressDialog: ProgressDialog

    private lateinit var passLama: String
    private lateinit var passBaru: String
    private lateinit var address: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        passLama = binding.passLama.text.toString()
//        getPassLama(passLama.toString())
        passBaru = binding.passBaru.text.toString()
        address = binding.address.text.toString()

        //check box click checker
        binding.chkDaftar.setOnCheckedChangeListener { _, isChecked ->
            binding.btnEdtProfil.isEnabled = isChecked
            binding.btnEdtProfil.isClickable = isChecked
        }

        binding.btnEdtProfil.setOnClickListener {
            passLama = binding.passLama.text.toString()
            passBaru = binding.passBaru.text.toString()
            address = binding.address.text.toString()

            //validasi password kosong
            if (passLama.isEmpty()){
                binding.passLama.error = "password harus diisi!"
                binding.passLama.requestFocus()
                return@setOnClickListener
            }

            //validasi password terlalu pendek
            if (passLama.length < 8){
                binding.passLama.error = "password terlalu pendek!"
                binding.passLama.requestFocus()
                return@setOnClickListener
            }

            //validasi password kosong
            if (passBaru.isEmpty()){
                binding.passBaru.error = "password harus diisi!"
                binding.passBaru.requestFocus()
                return@setOnClickListener
            }

            //validasi password terlalu pendek
            if (passBaru.length < 8){
                binding.passBaru.error = "password terlalu pendek!"
                binding.passBaru.requestFocus()
                return@setOnClickListener
            }

            //validasi email kosong
            if (address.isEmpty()){
                binding.address.error = "Alamat harus diisi!"
                binding.address.requestFocus()
                return@setOnClickListener
            }
            Edit()
        }
    }

//    private fun getPassLama() {
//        userData.get()
//            .addOnSuccessListener {
//                // set username
//                val passLama = "${it.data?.get("pass").toString()}"
//            }
//            .addOnFailureListener {
//                Log.e("Firestore error!", it.message.toString())
//                val passLama = ""
//            }
//    }

    fun Edit(){
        userData.get()
            .addOnSuccessListener {
                userData.update(mapOf(
                    "pass" to passBaru,
                    "address" to address,
                ))
            }
            .addOnFailureListener {
                Log.e("Failed to get data", it.message.toString())
            }
    }


}
