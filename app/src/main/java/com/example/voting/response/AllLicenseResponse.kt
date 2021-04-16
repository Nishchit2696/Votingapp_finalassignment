package com.example.voting.response

import com.example.voting.entity.License

class AllLicenseResponse (
        val success : Boolean?=true,
        val message: String?=null,
        val data : MutableList<License>? = null
)