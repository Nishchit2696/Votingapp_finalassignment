package com.example.voting.response

import com.example.voting.entity.Passport

data class DeletePassportResponse (
        val success : Boolean?=null,
        val data : Passport?=null,
)