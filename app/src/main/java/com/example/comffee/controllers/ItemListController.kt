package com.example.comffee.controllers

import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class ItemListController {
    private var con = DatabaseHandler.connect()

    private fun getCartId(): Int? {
        var cartId: Int? =null

        val query = "SELECT cart_id FROM cart WHERE user_id = '${ActiveUser.getUser_id()}'"

        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                cartId = rs.getInt("cart_id")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return cartId
    }

    fun tambahItemKeCart(item_id: Int): Boolean{
        val cart_id = getCartId()
        val query = "INSERT INTO cartitem (item_id, cart_id, quantity) VALUES ('$item_id', '$cart_id', 1)"

        return try {
            val stmt: Statement = con!!.createStatement()
            stmt.executeQuery(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }
}