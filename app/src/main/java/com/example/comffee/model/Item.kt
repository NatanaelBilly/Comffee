package com.example.comffee.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item (
    var item_id: Int,
    var item_name: String,
    var price: Int,
    var description:String
): Parcelable