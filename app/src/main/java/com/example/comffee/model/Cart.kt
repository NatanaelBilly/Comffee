package com.example.comffee.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Cart (
    var cart_id:Int,
    var user_id:Int,
):Parcelable