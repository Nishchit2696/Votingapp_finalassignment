package com.example.voting.api

import com.example.voting.entity.User
import com.example.voting.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserAPI {

    @POST("/User_insert")
    suspend fun registerUser(
        @Body user: User
    ): Response<LoginResponse>

    //Login user
    @FormUrlEncoded
    @POST("/login")
    suspend fun checkUser(
        @Field("username") username : String,
        @Field("password") password : String
    ): Response<LoginResponse>
}