package com.example.comffee

data class Item(
    var item_id : String ?= null,
    var nama_barang : String ?= null,
    var harga : Long ?= null,
    var imagePath : String ?= null
)
