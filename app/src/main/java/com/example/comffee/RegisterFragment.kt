package com.example.comffee

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentManager

class RegisterFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username: EditText = view.findViewById(R.id.username)
        val email: EditText = view.findViewById(R.id.email)
        val password: EditText = view.findViewById(R.id.password)
        val address: EditText = view.findViewById(R.id.address)
        val btnLogin: Button = view.findViewById(R.id.btn_login)
        val btnRegister: Button = view.findViewById(R.id.btn_register)
        btnLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login -> {
                val mFragmentManager = fragmentManager as FragmentManager
                val mLoginFragment = LoginFragment()
                mFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.frame_container,
                        mLoginFragment,
                        LoginFragment::class.java.simpleName
                    )
                    .addToBackStack(null)
                    .commit()
            }
            R.id.btn_register -> {
                val user = User(
                    "Richard",
                    "if-20034@student.ithb.ac.id",
                    "123456",
                    "Kopo, Bandung"
                )
                val intent = Intent(this@RegisterFragment, HomePage::class.java)
                intent.putExtra(intent.EXTRA_USER, user)
                startActivity(intent)
            }
        }
    }

}