package com.example.comffee.controllers

import android.database.SQLException
import android.view.SurfaceControl.Transaction
import com.example.comffee.model.User
import java.sql.ResultSet
import java.sql.Statement

class UserController {
    private var con = DatabaseHandler.connect()

    fun checkUserType(userId: String): String {
        // Check user type from the first letter of the id
        val firstLetterId = userId.subSequence(0, 1)

        if (firstLetterId == "M") {
            return "MEMBER"
        } else {
            var adminType = ""
            val query = "SELECT adminType FROM admins WHERE adminId = '$userId'"

            try {
                val stmt: Statement = con!!.createStatement()
                val rs: ResultSet = stmt.executeQuery(query)
                while (rs.next()) {
                    adminType = rs.getString("adminType")
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }

            if (adminType == "ADMIN") {
                return "ADMIN"
            } else {
                return "CASHIER"
            }
        }
    }

    fun setSingleton(user: User, userType: String, currentTransaction: com.example.comffee.model.Transaction?) {
        CurrentUser.setId(user.userId)
        CurrentUser.setUsername(user.username)
        CurrentUser.setEmail(user.email)
        CurrentUser.setPassword(user.password)
        CurrentUser.setAddress(user.address)
        CurrentUser.setType(userType)
        if (currentTransaction != null) {
            CurrentUser.setActiveTransaction(currentTransaction)
        }
    }

    fun setSingletonGoogle(inputEmail: String) {
        var user = User("", "", "", "", "")
        val query = "SELECT * FROM users WHERE email = '$inputEmail'"

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)

            while (rs.next()) {
                user = User(
                    rs.getString("userId"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("password")
                )
            }

            val userType = checkUserType(user.userId)
            val activeTransactionId = TransactionController.getActiveTransactionId(user.userId)
            var activeTransaction: Transaction? = null
            if (activeTransactionId == null) {
                setSingleton(user, userType, null)
            } else {
                activeTransaction = TransactionController.getTransactionDetail(activeTransactionId)
                setSingleton(user, userType, activeTransaction)
            }

//            val activeTransactionIdId
//            setSingleton(user, userType)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

}
