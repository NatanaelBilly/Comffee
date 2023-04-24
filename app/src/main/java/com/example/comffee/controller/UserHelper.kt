package com.example.comffee.controller

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.ContactsContract.CommonDataKinds.Email
import com.example.comffee.controller.DatabaseContract.UserColumns.Companion.EMAIL
import com.example.comffee.controller.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.example.comffee.controller.DatabaseContract.UserColumns.Companion._ID
import java.sql.SQLException

class UserHelper(context: Context) {

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var dataBaseHelper: DatabaseHelper
        private lateinit var database: SQLiteDatabase

        private var INSTANCE: UserHelper? = null

        fun getInstance(context: Context): UserHelper = INSTANCE ?: synchronized(this) {
            INSTANCE ?: UserHelper(context)
        }
    }

    init {
        dataBaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if(database.isOpen)
            database.close()
    }

    // Ambil Data
    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )
    }

    // Ambil Data dengan ID
    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    // Ambil Data dengan email
    fun queryByEmail(email: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$EMAIL = ?",
            arrayOf(email),
            null,
            null,
            null,
            null
        )
    }

    // Simpan Data
    fun insert(values: ContentValues?): Long {
        return  database.insert(DATABASE_TABLE, null, values)
        //        return database.insert(DATABASE_TABLE, null, values)
    }

    // Update Data dengan ID
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    // Hapus Data dengan ID
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = $id", null)
    }
}