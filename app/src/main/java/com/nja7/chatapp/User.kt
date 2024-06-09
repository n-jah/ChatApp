package com.nja7.chatapp

class User {
    var name: String? = null
    var email: String? = null
    var uid: String? = null
    var token: String? = null
    constructor(){}
    constructor(name: String?, email: String?, uid: String?, token: String?) {
        this.name = name
        this.email = email
        this.uid = uid
        this.token = token
    }
    constructor(name: String?, email: String?, uid: String?) {
        this.name = name
        this.email = email
        this.uid = uid
    }


}