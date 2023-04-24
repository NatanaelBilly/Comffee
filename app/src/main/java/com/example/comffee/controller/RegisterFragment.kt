package com.example.comffee.controller

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
import com.example.comffee.R
import com.example.comffee.model.User

class RegisterFragment : Fragment(), View.OnClickListener {


    private var user: User? = null

    private lateinit var username: TextView
    private lateinit var email: TextView
    private lateinit var password: TextView
    private lateinit var address: TextView
    private lateinit var btnLogin: Button
    private lateinit var btnRegis: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = view.findViewById(R.id.in_username)
        email = view.findViewById(R.id.in_email)
        password = view.findViewById(R.id.in_password)
        address = view.findViewById(R.id.in_address)
        btnLogin = view.findViewById(R.id.btn_login)
        btnRegis = view.findViewById(R.id.btn_regis)

        btnLogin.setOnClickListener(this)
        btnRegis.setOnClickListener(this)
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
                        mLoginFragment, LoginFragment::class.java.simpleName
                )
                .addToBackStack(null)
                .commit()
        }
            R.id.btn_regis -> {

            }
        }
    }

}