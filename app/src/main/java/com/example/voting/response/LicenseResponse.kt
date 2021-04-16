package com.example.voting.response

import com.example.voting.entity.License

data class LicenseResponse (
        val success : Boolean?=null,
        val message:String?=null,
        val data : License?=null,
)