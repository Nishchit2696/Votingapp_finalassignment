package com.example.wearvoting.response

data class LoginResponse(
    val success : Boolean? = null,
    val token : String ? = null,
    val message : String ? = null
)