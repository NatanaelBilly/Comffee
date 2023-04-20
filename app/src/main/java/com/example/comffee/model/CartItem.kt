package com.example.comffee.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartItem (
    var cartitem_id:Int,
    var item_id:Int,
    var cart_id:Int,
    var quantity:Int
    ):Parcelable
