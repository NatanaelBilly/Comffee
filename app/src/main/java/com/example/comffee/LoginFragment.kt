package com.example.comffee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager

class LoginFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login -> {
                val mFragmentManager = fragmentManager as FragmentManager
                val mHomePage = HomePage()
                mFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.frame_container,
                        mHomePage,
                        LoginFragment::class.java.simpleName
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

}