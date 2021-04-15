package com.example.voting.response

import com.example.voting.entity.Passport

data class PassportResponse (
        val success : Boolean?=null,
        val message:String?=null,
        val data : Passport?=null,
        )