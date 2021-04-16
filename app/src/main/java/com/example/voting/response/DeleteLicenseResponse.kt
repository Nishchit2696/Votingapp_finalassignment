package com.example.voting.response

import com.example.voting.entity.License


data class DeleteLicenseResponse  (
        val success : Boolean?=null,
        val data : License?=null,
)