package com.example.comffee.controllers

import android.database.SQLException
import com.example.comffee.controllers.CartControllers
import com.example.comffee.controllers.DatabaseHandler
import com.example.comffee.model.User
import java.sql.ResultSet
import java.sql.Statement

class RegisterControllers {
    private var con = DatabaseHandler.connect()

    fun registerUser(user: User): Boolean {
            val query = "INSERT INTO user('username','email','password','address') VALUES ('${user.username}', '${user.email}', '${user.password}','${user.address}')"
            val query2= "SELECT user_id from user where username='${user.username}'"

            return try {
                val stmt: Statement = con!!.createStatement()
                stmt.executeQuery(query)
                val userId= stmt.executeQuery(query2)
                val query3 = "INSERT INTO cart('user_id') VALUES ('$userId')"
                stmt.executeQuery(query3)
                true
            } catch (e: SQLException) {
                e.printStackTrace()
                false
            }
    }
}