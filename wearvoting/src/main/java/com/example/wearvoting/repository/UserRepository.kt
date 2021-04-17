package com.example.wearvoting.repository


import com.example.wearvoting.api.MyApiRequest
import com.example.wearvoting.api.ServiceBuilder
import com.example.wearvoting.api.UserAPI
import com.example.wearvoting.entity.User
import com.example.wearvoting.response.LoginResponse


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