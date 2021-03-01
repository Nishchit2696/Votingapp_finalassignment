package com.example.voting.api

import com.example.voting.entity.Passport
import com.example.voting.repository.PassportRepostory
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface PassportAPI {
    //Add Student
    @POST("/passport_insert")
    suspend fun addStudent(
            @Header("Authorization") token : String,
            @Body passport: Passport
    ): Response<PassportRepostory>

    //get all students
    @GET("/passport/fetchall")
    suspend fun getAllStudents(
            @Header("Authorization") token : String,
    ): Response<PassportRepostory>

    //delete student

    @DELETE("passport/delete/:vid")
    suspend fun deleteStudent(
            @Header("Authorization") token: String,
            @Path("id") id: String
    ): Response<PassportRepostory>


}
