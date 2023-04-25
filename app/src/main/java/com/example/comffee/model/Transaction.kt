package com.example.comffee.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Time
import java.sql.Timestamp
import java.time.LocalDateTime

@Parcelize
class Transaction(
    var transaction_id: Int,
    var status: TransactionStatus,
    var items: ArrayList<Item>,
    var itemQuantities: ArrayList<Int>
) : Parcelable