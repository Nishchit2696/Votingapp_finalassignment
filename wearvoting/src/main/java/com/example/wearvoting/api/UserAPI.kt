package com.example.wearvoting.api


import com.example.wearvoting.entity.User
import com.example.wearvoting.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserAPI {

    @POST("User_insert")
    suspend fun registerUser(
        @Body user: User
    ): Response<LoginResponse>

    //Login user
    @FormUrlEncoded
    @POST("login")
    suspend fun checkUser(
        @Field("Citizenship") Citizenship : String,
        @Field("Password") password : String
    ): Response<LoginResponse>
}