package com.example.wearvoting.api

import com.example.wearvoting.response.AllPassportResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface PassportAPI {
@GET("passportwear/fetch")
suspend fun getAllPassport(
    @Header("Authorization") token : String,
): Response<AllPassportResponse>
}