package com.example.voting.api

import com.example.voting.entity.Passport
import com.example.voting.response.PassportResponse
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


}
