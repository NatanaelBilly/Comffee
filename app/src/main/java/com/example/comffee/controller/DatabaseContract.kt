package com.example.comffee.controller

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "user"
            const val _ID = "_id"
            const val USERNAME = "username"
            const val EMAIL = "email"
            const val PASSWORD = "password"
            const val ADDRESS = "address"
        }
    }
}