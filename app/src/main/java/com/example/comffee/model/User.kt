package com.example.comffee.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User(
    var user_id: Int,
    var username: String,
    var email: String,
    var password: String,
    var address: String
) : Parcelable