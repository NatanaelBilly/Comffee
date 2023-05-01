package com.example.comffee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.comffee.databinding.ActivityHistoryBinding
import com.example.comffee.databinding.ActivityHomepageBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class History : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private val auth = FirebaseAuth.getInstance()

    private lateinit var adapter:NoteAdapter
    private lateinit var noteHelper: NoteHelper
    private lateinit var progressBar: ProgressBar
    private lateinit var rv_notes: RecyclerView
    private lateinit var fab_add: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {

            val loginIntent = Intent(this, Homepage::class.java)
            startActivity(loginIntent)
        }
    }
}