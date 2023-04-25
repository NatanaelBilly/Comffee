package com.example.comffee.controllers

import android.database.SQLException
import com.example.comffee.controller.ActiveUser
import com.example.comffee.controller.DatabaseHandler
import com.example.comffee.model.Transaction
import com.example.comffee.model.User
import java.sql.ResultSet
import java.sql.Statement

class UserControllers {
    private var con = DatabaseHandler.connect()

    fun setSingleton(user: User, activeTransaction: Transaction?) {
        ActiveUser.setUser_id(user.user_id)
        ActiveUser.setUsername(user.username)
        ActiveUser.setEmail(user.email)
        ActiveUser.setPassword(user.password)
        ActiveUser.setAdress(user.address)
        if (activeTransaction != null) {
            ActiveUser.setActiveTransaction(activeTransaction)
        }
    }

    fun setSingletonGoogle(inputEmail: String) {
        var user = User(0, "", "", "", "")
        val query = "SELECT * FROM users WHERE email = '$inputEmail'"

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)

            while (rs.next()) {
                user = User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("address")
                )
            }
            val activeTransactionId = TransactionControllers.getActiveTransactionId(user.user_id)
            var activeTransaction: Transaction? = null
            if (activeTransactionId == null) {
                setSingleton(user, null)
            } else {
                activeTransaction = TransactionControllers.getTransactionDetail(activeTransactionId)
                setSingleton(user, activeTransaction)
            }

//            val activeTransactionIdId
//            setSingleton(user, userType)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

}