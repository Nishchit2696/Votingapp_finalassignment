package com.example.voting.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity
//class User (
    //var Frt: String? = null,
    //var lat: String? = null,
    //var eml: String? = null,
    //var pro: String? = null,
    //var cty: String? = null,
    //var ctz: String? = null,
    //var phn: String? = null,
    //var psw: String? = null,
    //var rpw: String? = null

//){
    //@PrimaryKey(autoGenerate = true)
    //var userId : Int = 0
//}

data class User(
    val _id : String? = null,
    val Firstname : String? = null,
    val Lastname : String? = null,
    val Citizenship : String? = null,
    val Phonenumber : String? = null,
    val Password : String? = null
)