package com.example.comffee.controllers

import android.util.Log
import com.example.comffee.controller.ActiveUser
import com.example.comffee.model.*
import com.example.comffee.controller.DatabaseHandler
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.sql.Timestamp

class TransactionControllers {

    companion object {
        private var con = DatabaseHandler.connect()

        fun getTransactionDetail(transaction_id: Int): Transaction {
            val transaction_id = transaction_id

            var status = TransactionStatus.BERLANGSUNG
            val (items, itemQuantities) = getItemFromTransaction(transaction_id)

//            if (status_string == "PAID") {
//                status = TransactionEnum.PAID
//            } else if (status_string == "PENDING") {
//                status = TransactionEnum.PENDING
//            } else if (status_string == "CANCELED") {
//                status = TransactionEnum.CANCELED
//            }

            val transaction: Transaction = Transaction(
                transaction_id,
                status,
                items,
                itemQuantities
            )
            return transaction
        }

        fun getTransactionData(): ArrayList<Transaction> {
            val transactionIds: ArrayList<Int> = getTransactionGeneralData()
            val transactions: ArrayList<Transaction> = ArrayList()

            for (i in transactionIds.indices) {
                var status: TransactionStatus = TransactionStatus.BERLANGSUNG
                val (items, itemQuantities) = getItemFromTransaction(transactionIds[i])

                val transaction = Transaction(
                    transactionIds[i],
                    status,
                    items,
                    itemQuantities
                )

                transactions.add(transaction)
            }

            return transactions
        }

        private fun getItemFromTransaction(transactionId: Int): Pair<ArrayList<Item>, ArrayList<Int>> {
            val items: ArrayList<Item> = ArrayList()
            val itemQuantities: ArrayList<Int> = ArrayList()

            val query = "SELECT i.item_id, i.item_name, i.price, ci.quantity " +
                    "FROM item i " +
                    "JOIN cartitem ci " +
                    "ON i.item_id = ci.item_id " +
                    "WHERE ci.cart_id = '${transactionId}'"

            try {
                val stmt: Statement = con!!.createStatement()
                val rs: ResultSet = stmt.executeQuery(query)
                while (rs.next()) {
                    val item = Item(
                        rs.getInt("item_id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("description")
                    )
                    val quantity = rs.getInt("menuQuantity")
                    items.add(item)
                    itemQuantities.add(quantity)
                }
            } catch (e: android.database.SQLException) {
                e.printStackTrace()
            }

            return Pair(items, itemQuantities)
        }

        fun getActiveTransactionId(userId: Int): Int? {
            var activeTransactionId: Int? = null

            val query =
                "SELECT transaction_id FROM transaction WHERE user_id = '${userId}' AND status = 'BERLANGSUNG'"

            try {
                val stmt: Statement = con!!.createStatement()
                val rs: ResultSet = stmt.executeQuery(query)

                while (rs.next()) {
                    activeTransactionId = rs.getInt("transactionId")
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }

            return activeTransactionId
        }

        private fun getTransactionGeneralData(): ArrayList<Int> {
            var transactionGeneralData: ArrayList<Int> = ArrayList()

            val query =
                "SELECT transaction_id FROM transaction WHERE user_id = '${ActiveUser.getUser_id()}'"

            try {
                val stmt: Statement = con!!.createStatement()
                val rs: ResultSet = stmt.executeQuery(query)

                while (rs.next()) {
                    transactionGeneralData.add(rs.getInt("transactionId"))
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }

            return transactionGeneralData
        }
    }
}