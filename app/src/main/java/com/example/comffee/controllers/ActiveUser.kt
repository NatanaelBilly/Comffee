package com.example.comffee.controller


object ActiveUser {

    // Singleton

    private var user_id: String = "NoId"
    private var username: String = "NoUsername"
    private var email: String = "NoEmail"
    private var password: String = "NoPassword"
    private var address: String = "NoAddress" // MEMBER, ADMIN, CASHIER

    // Setter

    fun setUser_id(user_id: String) {
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

    // Getter

    fun getUser_id(): String {
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
}