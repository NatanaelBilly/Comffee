package com.example.comffee.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp

@Parcelize
class Transaction(
    var transactionId: String,
    var table: Table?,
    var status: TransactionEnum,
    var items: ArrayList<Items>,
    var itemsQuantities: ArrayList<Int>
) : Parcelable