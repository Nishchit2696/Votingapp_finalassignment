package com.example.voting.repository

import com.example.voting.api.MyApiRequest
import com.example.voting.api.ServiceBuilder
import com.example.voting.api.UserAPI
import com.example.voting.entity.User
import com.example.voting.response.LoginResponse

class UserRepository
    : MyApiRequest() {

    private val userAPI =
        ServiceBuilder.buildService(UserAPI::class.java)

    //register User

    suspend fun registerUser(user: User): LoginResponse {
        return apiRequest {
            userAPI.registerUser(user)
        }
    }
    //login user
    suspend fun checkUser(Citizenship:String,password:String):LoginResponse{
        return apiRequest {
            userAPI.checkUser(Citizenship,password)
        }
    }
}