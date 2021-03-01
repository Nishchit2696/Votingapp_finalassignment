package com.example.voting.repository

import com.example.voting.api.MyApiRequest
import com.example.voting.api.PassportAPI
import com.example.voting.api.ServiceBuilder
import com.example.voting.entity.Passport
import com.example.voting.response.PassportResponse

class PassportRepository
    : MyApiRequest() {

    private val PassportAPI =
            ServiceBuilder.buildService(PassportAPI::class.java)

    //Add Student

    suspend fun addpassport(passport: Passport): PassportResponse {
        return apiRequest {
            PassportAPI.addpassport(
                    ServiceBuilder.token!!, passport
            )
        }
    }

    suspend fun getpassport(): PassportResponse {
        return apiRequest {
            PassportAPI.getpassport(
                    ServiceBuilder.token!!
            )
        }
    }
}