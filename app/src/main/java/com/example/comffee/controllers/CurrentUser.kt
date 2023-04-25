package com.example.comffee.controllers

import com.example.comffee.model.Transaction

object CurrentUser {


    // Singleton

    private var id: String = "NoId"
    private var username: String = "NoFirstName"
    private var email: String = "NoEmail"
    private var password: String = "NoPassword"
    private var address: String = "NoAddress"
    private var activeTransaction: Transaction? = null // get active transaction
    private var userType: String = "NoType" // MEMBER, ADMIN, CASHIER

    // Setter

    fun setId(id: String) {
        this.id = id
    }
    fun setUsername(username: String) {
        this.username = username
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun setAddress(address: String) {
        this.email = address
    }

    fun setType(userType: String){
        this.userType = userType
    }

    fun setActiveTransaction(activeTransaction: Transaction?){
        this.activeTransaction = activeTransaction
    }

    // Getter

    fun getId(id: String) {
        this.id = id
    }
    fun getUsername(username: String) {
        this.username = username
    }

    fun getEmail(email: String) {
        this.email = email
    }

    fun getPassword(password: String) {
        this.password = password
    }

    fun getAddress(address: String) {
        this.email = address
    }

    fun getType(userType: String){
        this.userType = userType
    }

    fun getActiveTransaction(activeTransaction: Transaction?){
        this.activeTransaction = activeTransaction
    }
}