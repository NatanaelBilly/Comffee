package com.example.comffee.controller

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import com.example.comffee.R

class LoginFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email: EditText = view.findViewById(R.id.email)
        val password: EditText = view.findViewById(R.id.password)
        val btnLogin: Button = view.findViewById(R.id.btn_login)
        val btnRegister: Button = view.findViewById(R.id.btn_register)
        btnLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login -> {
                val login = Intent (this@LoginFragment, LoginFragment::class.java)
                    //Intent(this@LoginFragment, intent::class.java)
                login.putExtra(login.EXTRA_EMAIL, "if-20034@student.ithb.ac.id")
                login.putExtra(login.EXTRA_PASSWORD, "123456")
                startActivity(login)
            }
            R.id.btn_register -> {
                val mFragmentManager = fragmentManager as FragmentManager
                val mRegisterFragment = RegisterFragment()
                mFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.frame_container,
                        mRegisterFragment,
                        LoginFragment::class.java.simpleName
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

}
