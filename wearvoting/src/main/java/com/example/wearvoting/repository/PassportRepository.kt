package com.example.wearvoting.repository

import com.example.wearvoting.api.MyApiRequest
import com.example.wearvoting.api.PassportAPI
import com.example.wearvoting.api.ServiceBuilder
import com.example.wearvoting.response.AllPassportResponse

class PassportRepository
    : MyApiRequest() {

        private val passportAPI =
            ServiceBuilder.buildService(PassportAPI::class.java)


    suspend fun getAllPassport(): AllPassportResponse{
        return apiRequest {
            passportAPI.getAllPassport(
                ServiceBuilder.token!!
            )
        }
    }

}