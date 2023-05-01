package com.example.comffee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.comffee.databinding.ActivityHomepageBinding
import com.example.comffee.databinding.ActivityOrderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Order : AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding

    private val auth = FirebaseAuth.getInstance()

    private val firestore = Firebase.firestore

    private lateinit var itemId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {

            val loginIntent = Intent(this, Homepage::class.java)
            startActivity(loginIntent)
        }

        getItemData()
    }

    private fun getItemData() {
        itemId = "bk1"
        val itemData = firestore.collection("items").document(itemId)
        itemData.get()
            .addOnSuccessListener {

                println("data = $it")
                // set Items
                val item = "Nama : ${it.data?.get("nama_barang").toString()}" +
                        "\n Harga : ${it.data?.get("harga").toString()}"
                binding.tvItem.text = item

            }
            .addOnFailureListener {
                Log.e("Firestore error!", it.message.toString())
            }
    }
}