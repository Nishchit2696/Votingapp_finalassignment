package com.example.voting.api

import com.example.voting.entity.Passport
import com.example.voting.response.ImageResponse
import com.example.voting.response.PassportResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface PassportAPI {
    //Add Student
    @POST("/passport_insert")
    suspend fun addpassport(
            @Header("Authorization") token : String,
            @Body passport: Passport
    ): Response<PassportResponse>

    //get all students
    @GET("/passport/fetchall")
    suspend fun getpassport(
            @Header("Authorization") token : String,
    ): Response<PassportResponse>

    //delete student

    @DELETE("passport/delete/:vid")
    suspend fun deleteStudent(
            @Header("Authorization") token: String,
            @Path("id") id: String
    ): Response<PassportResponse>

    @Multipart
    @PUT("student/{id}/photo")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Part file: MultipartBody.Part
    ): Response<ImageResponse>

}
