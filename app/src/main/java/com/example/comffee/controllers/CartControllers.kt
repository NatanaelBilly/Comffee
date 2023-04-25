package com.example.comffee.controllers
import com.example.comffee.model.Cart
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class CartControllers {
    private var con = DatabaseHandler.connect()
//    fun listItemCartById(cart_id:Int):Quadruple<String,String,Int,Int>?{
//        val
//    }

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
    fun removeItemOnCart(item_id:Int):Boolean{
        val cart_id= getCartId()
        val query = "UPDATE cartitem SET quantity = NULL WHERE cart_id = '$cart_id' And item_id= '$item_id'"

        return try {
            val stmt: Statement = con!!.createStatement()
            stmt.executeQuery(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

    fun tambahQuantityItem(item_id: Int): Boolean{
        val cart_id = getCartId()
        val query = "UPDATE cartitem SET quantity = quantity + 1 WHERE cart_id = '$cart_id' AND item_id = '$item_id'"

        return try {
            val stmt: Statement = con!!.createStatement()
            stmt.executeQuery(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

    fun kurangQuantityItem(item_id: Int): Boolean{
        val cart_id = getCartId()
        val query = "UPDATE cartitem SET quantity = quantity - 1 WHERE cart_id = '$cart_id' AND item_id = '$item_id'"

        return try {
            val stmt: Statement = con!!.createStatement()
            stmt.executeQuery(query)
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

    fun totalHarga(): Int?{
        var total_harga:Int? = null

        val cart_id = getCartId()
        val query = "SELECT SUM(item.harga * cartitem.quantity) AS total_harga from cartitem join item on cartitem.id_item=item.id_item where cartitem.cart_id='$cart_id' "

        try {
            val stmt: Statement = con!!.createStatement()
            val result: ResultSet = stmt.executeQuery(query)
            if (result.next()) {
                total_harga = result.getInt("total_harga")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return total_harga
    }

    fun getCartData(): Cart {
        val cart_id = getCartId()
        val user_id = getTableFromCart()
        val books = getBookFromCart()
        val (menus, menuQuantities) = getMenuFromCart()

        val cart = Cart(cart_id, user_id)

        return cart
    }
}