package com.example.comffee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentManager


class WelcomePage : Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnLogin: Button = view.findViewById(R.id.btn_login)
        val btnRegister: Button = view.findViewById(R.id.btn_register)
        btnLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)
    }

    override fun onClick(v: View) {
//        when (v.id) {
//            R.id.btn_login -> {
//                val mFragmentManager = fragmentManager as FragmentManager
//                val mLoginFragment = LoginFragment()
//                mFragmentManager
//                    .beginTransaction()
//                    .replace(
//                        R.id.frame_container,
//                        mLoginFragment,
//                        LoginFragment::class.java.simpleName
//                    )
//                    .addToBackStack(null)
//                    .commit()
//            }
//            R.id.btn_login -> {
//                val mFragmentManager = fragmentManager as FragmentManager
//                val mRegisterFragment = RegisterFragment()
//                mFragmentManager
//                    .beginTransaction()
//                    .replace(
//                        R.id.frame_container,
//                        mRegisterFragment,
//                        LoginFragment::class.java.simpleName
//                    )
//                    .addToBackStack(null)
//                    .commit()
//            }
//        }
    }
}