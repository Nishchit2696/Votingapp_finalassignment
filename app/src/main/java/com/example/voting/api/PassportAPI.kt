package com.example.voting.api

import com.example.voting.entity.Passport
import com.example.voting.response.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface PassportAPI {
    //Add passport
    @POST("product_insert_app")
    suspend fun insertPassport(
            @Header("Authorization") token : String,
            @Body passport: Passport
    ): Response<PassportResponse>

    //get all students
    @GET("passport/fetchall")
    suspend fun getpassport(
            @Header("Authorization") token : String,
    ): Response<AllPassportResponse>

    //delete student

    @DELETE("passport/delete/{id}")
    suspend fun deletepassport(
            @Header("Authorization") token: String,
            @Path("id") id: String
    ): Response<DeletePassportResponse>

    @Multipart
    @PUT("image/update/{id}")
    suspend fun uploadImage(
        @Path("id") id: String,
        @Part file: MultipartBody.Part
    ): Response<ImageResponse>

    @PUT("passport/update/{id}")
    suspend fun updatepassport(
            @Header("Authorization") token: String,
            @Path("id") id: String,
            @Body passport: Passport
    ): Response<UdatePassportResponse>

}
