package com.example.comffee

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.comffee.databinding.ActivityOrderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Order : AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding

//    private val auth = FirebaseAuth.getInstance()

    private val firestore = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser

    private lateinit var itemId: String
    private var qty: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.icon_home->{
                    val intent = Intent(this, Homepage::class.java)
                    startActivity(intent)
                }
                R.id.icon_profile->{
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                }
                R.id.icon_history->{
                    val intent = Intent(this, com.example.comffee.History::class.java)
                    startActivity(intent)
                }
                R.id.icon_order->{
                    val intent = Intent(this, ItemList::class.java)
                    startActivity(intent)
                    // Biar gada transisi blink
                    overridePendingTransition(0, 0)
                }
                R.id.icon_shopping_cart->{
                    val intent = Intent(this, ItemList::class.java)
                    startActivity(intent)
                }
                R.id.icon_logout->{
                    auth.signOut()
                    val loginIntent = Intent(this, Login::class.java)
                    startActivity(loginIntent)
                }
            }
            true
        }

//        binding.btnBack.setOnClickListener {
//
//            val loginIntent = Intent(this, Homepage::class.java)
//            startActivity(loginIntent)
//        }

        getItemData()
        itemId = "bk1"
        qty = 1

        //button buat order
//        binding.order.setOnClickListener {
//            itemId = getItemId()
//        itemId = dari id di list recyler view

//            qty = getItemQty()
//        qty = dari data di list recyler view

//            addtoCart(itemId, qty)
//        }
    }

    // isi list id item
    private fun getItemData() {
        val list: MutableList<String> = ArrayList()
        firestore.collection("items").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        list.add(document.id)
                        println("listnya: $list")
                        val stringBuilder = StringBuilder()
                        for (item in list) {
                            stringBuilder.append(item).append("\n")
                        }
                        val result = stringBuilder.toString()
                        println("print list: $result")
                        binding.tvItem.text = result
                    }
                    Log.d(TAG, list.toString())
                } else {
                    Log.d(TAG, "Error getting documents: ", task.exception)
                }
            }
    }

    // isi listnya nama_barang
//    private fun getItemData() {
//        firestore.collection("items").get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val list: MutableList<String> = ArrayList()
//                    for (document in task.result) {
//                        firestore.collection("items").document(document.id).get()
//                            .addOnSuccessListener {
//                                list.add(it.data?.get("nama_barang").toString())
//                                println("listnya: $list")val stringBuilder = StringBuilder()
//                                for (item in list) {
//                                    stringBuilder.append(item).append("\n")
//                                }
//                                val result = stringBuilder.toString()
//                                println("print list: $result")
//                                binding.tvItem.text = result
//                            }
//                            .addOnFailureListener {
//                                Log.e("Firestore error!", it.message.toString())
//                            }
//                    }
//                    Log.d(TAG, list.toString())
//                } else {
//                    Log.d(TAG, "Error getting documents: ", task.exception)
//                }
//            }
//    }

    // pake keymap
//    private fun getItemData() {
//        firestore.collection("items").get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val list: MutableList<Pair<String, String>> = ArrayList()
//                    for (document in task.result) {
//                        firestore.collection("items").document(document.id).get()
//                            .addOnSuccessListener {
//                                list.add(Pair(document.id,it.data?.get("nama_barang").toString()))
//                                println("listnya: $list")
//                                val stringBuilder = StringBuilder()
//                                for (item in list) {
//                                      stringBuilder.append(item).append("\n")
//                                }
//                                val result = stringBuilder.toString()
//                                println("print list: $result")
//                                binding.tvItem.text = result
//                            }
//                            .addOnFailureListener {
//                                Log.e("Firestore error!", it.message.toString())
//                            }
//                    }
//                    Log.d(TAG, list.toString())
//                } else {
//                    Log.d(TAG, "Error getting documents: ", task.exception)
//                }
//            }
//    }

    // menambah item ke keranjang
    private fun addtoCart(itemId: String, qty: Int) {
        // value yang mau ditambahkan ke db
        val item = hashMapOf(
            "itemId" to itemId,
            "qty" to qty
        )

        //add data to db
        firestore.collection("users")
            .document(currentUser?.email.toString())
            .collection("keranjang")
            .document("cartUser")
            .set(item)
            .addOnSuccessListener {
                println("Sukses! Barang telah ditambahkan")
            }
            .addOnFailureListener{
                println("Error")
            }
    }
}