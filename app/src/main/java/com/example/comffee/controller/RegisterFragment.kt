package com.example.comffee.controller

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.comffee.R
import com.example.comffee.model.User

class RegisterFragment : Fragment(), View.OnClickListener {

    private var isUsed = false
    private var user: User? = null
    private var position: Int = 0

    private lateinit var userHelper: UserHelper
    private lateinit var in_username: TextView
    private lateinit var in_email: TextView
    private lateinit var in_password: TextView
    private lateinit var in_address: TextView
    private lateinit var btn_login: Button
    private lateinit var btn_regis: Button


    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_ADD = 100
        const val RESULT_ADD = 101
        const val REQUEST_UPDATE = 200
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        in_username = view.findViewById(R.id.in_username)
        in_email = view.findViewById(R.id.in_email)
        in_password = view.findViewById(R.id.in_password)
        in_address = view.findViewById(R.id.in_address)
        btn_login = view.findViewById(R.id.btn_login)
        btn_regis= view.findViewById(R.id.btn_register)
        btn_login.setOnClickListener(this)
        btn_regis.setOnClickListener(this)
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
                val username = in_username.text.toString().trim()
                val email = in_email.text.toString().trim()
                val password = in_password.text.toString().trim()
                val address = in_address.text.toString().trim()

                if (username.isEmpty()) {
                    in_username.error = "Filed can not be blank"
                    return
                }
                if (email.isEmpty()) {
                    in_email.error = "Filed can not be blank"
                    return
                }
                if (password.isEmpty()) {
                    in_password.error = "Filed can not be blank"
                    return
                }
                if (address.isEmpty()) {
                    in_address.error = "Filed can not be blank"
                    return
                }

                user?.username = username
                user?.email = email
                user?.password = password
                user?.address = address

                val intent = Intent()
                intent.putExtra(EXTRA_USER, user)
                intent.putExtra(EXTRA_POSITION, position)

                val values = ContentValues()
                values.put(DatabaseContract.UserColumns.USERNAME, username)
                values.put(DatabaseContract.UserColumns.EMAIL, email)
                values.put(DatabaseContract.UserColumns.PASSWORD, password)
                values.put(DatabaseContract.UserColumns.ADDRESS, address)

                if (isUsed) {
                } else {

                }
            }
        }
    }
}