package com.example.comffee.controllers


import android.database.SQLException
import android.util.Log
import com.example.comffee.controllers.DatabaseHandler
import com.example.comffee.model.Transaction
import com.example.comffee.model.User
import java.sql.ResultSet
import java.sql.Statement

class LoginControllers {
    private var con = DatabaseHandler.connect()

    fun getLoginData(inputEmail: String, inputPassword: String): Boolean {
        var user = User(0, "", "", "", "")
        val query =
            "SELECT * FROM user WHERE email = '$inputEmail' AND password = '$inputPassword'"

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)

            while (rs.next()) {
                user = User(
                    rs.getInt("userId"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("password")
                )
            }

            // Recheck Login Data
            val isLoggedIn: Boolean = checkLoginData(user, inputEmail, inputPassword)

            if (isLoggedIn) {
                val control = UserControllers()
                val activeTransactionId = TransactionControllers.getActiveTransactionId(user.user_id)
                var activeTransaction: Transaction? = null
                if (activeTransactionId == null) {
                    control.setSingleton(user, null)
                } else {
                    activeTransaction =
                        TransactionControllers.getTransactionDetail(activeTransactionId)
                    control.setSingleton(user, activeTransaction)
                }

                if (activeTransactionId != null) {
                    Log.d("TAG", activeTransactionId.toString())
                } else {
                    Log.d("TAG", "no active trans")
                }
                return true
            } else {
                return false
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            return false
        }
    }

    private fun checkLoginData(user: User, inputEmail: String, inputPassword: String): Boolean {
        return inputEmail == user.email && inputPassword == user.password
    }

}