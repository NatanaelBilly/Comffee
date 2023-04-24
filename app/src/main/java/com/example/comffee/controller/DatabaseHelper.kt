package com.example.comffee.controller

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.comffee.controller.DatabaseContract.UserColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbcomffee"
        private const val DATABASE_VERSION = 1
        private val SQL_CREATE_TABLE_USER  =
            "CREATE TABLE $TABLE_NAME" +
                    "(${DatabaseContract.UserColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " ${DatabaseContract.UserColumns.USERNAME} TEXT NOT NULL," +
                    " ${DatabaseContract.UserColumns.EMAIL} TEXT NOT NULL," +
                    " ${DatabaseContract.UserColumns.PASSWORD} TEXT NOT NULL," +
                    " ${DatabaseContract.UserColumns.ADDRESS} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersionError: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}