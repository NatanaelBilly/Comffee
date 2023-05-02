package com.example.comffee.controllers

import com.example.comffee.model.Transaction


object ActiveUser {

    // Singleton

    private var user_id: Int = 0
    private var username: String = "NoUsername"
    private var email: String = "NoEmail"
    private var password: String = "NoPassword"
    private var address: String = "NoAddress"
    private var activeTransaction: Transaction? = null

    // Setter

    fun setUser_id(user_id: Int) {
        this.user_id = user_id
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

    fun setAdress(address: String){
        this.address = address
    }

    fun setActiveTransaction(activeTransaction: Transaction?){
        this.activeTransaction = activeTransaction
    }

    // Getter

    fun getUser_id(): Int {
        return this.user_id
    }

    fun getUsername(): String {
        return this.username
    }

    fun getEmail(): String {
        return this.email
    }

    fun getPassword(): String {
        return this.password
    }

    fun getAdress(): String {
        return this.address
    }

    fun getActiveTransaction(): Transaction? {
        return this.activeTransaction
    }

}