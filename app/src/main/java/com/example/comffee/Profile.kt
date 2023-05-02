package com.example.comffee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.comffee.databinding.ActivityHomepageBinding
import com.example.comffee.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Profile : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private val firestore = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser
    val userData = firestore.collection("users").document(currentUser?.email.toString())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.btnEdit.setOnClickListener {
//
//            val loginIntent = Intent(this, EditProfil::class.java)
//            startActivity(loginIntent)
//        }

        binding.btnBack.setOnClickListener {

            val loginIntent = Intent(this, Homepage::class.java)
            startActivity(loginIntent)
        }
        readUserData()

    }

    private fun readUserData() {

        userData.get()
            .addOnSuccessListener {
                // set username
                val profil = "Halo, ${it.data?.get("username").toString()}" +
                        "\n" +
                        "Berikut data profil kamu!" +
                        "\n" +
                        " Email: ${it.data?.get("email").toString()}" +
                        "\n" +
                        "Alamat: ${it.data?.get("address").toString()}"
                binding.tvUser.text = profil

            }
            .addOnFailureListener {
                Log.e("Firestore error!", it.message.toString())
            }
    }
}
