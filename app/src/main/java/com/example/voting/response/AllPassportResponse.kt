package com.example.voting.response

import com.example.voting.entity.Passport

class AllPassportResponse (
        val success : Boolean?=true,
        val message: String?=null,
        val data : MutableList<Passport>? = null
)