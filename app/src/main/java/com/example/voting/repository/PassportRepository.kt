package com.example.voting.repository

import com.example.voting.api.MyApiRequest
import com.example.voting.api.PassportAPI
import com.example.voting.api.ServiceBuilder
import com.example.voting.entity.Passport
import com.example.voting.response.AllPassportResponse
import com.example.voting.response.DeletePassportResponse
import com.example.voting.response.ImageResponse
import com.example.voting.response.PassportResponse
import okhttp3.MultipartBody

class PassportRepository
    : MyApiRequest() {

    private val passportAPI =
            ServiceBuilder.buildService(PassportAPI::class.java)

    //Add Student

    suspend fun insertPassport(passport: Passport): PassportResponse {
        return apiRequest {
            passportAPI.insertPassport( ServiceBuilder.token!!, passport)
        }
    }

    suspend fun getpassport(): AllPassportResponse {
        return apiRequest {
            passportAPI.getpassport(
                    ServiceBuilder.token!!
            )
        }
    }

    suspend fun deletepassport(passport: String): DeletePassportResponse {
        return apiRequest {
            passportAPI.deletepassport(
                    ServiceBuilder.token!!,passport
            )
        }
    }


    suspend fun uploadImage(id: String, body: MultipartBody.Part)
            : ImageResponse {
        return apiRequest {
            passportAPI.uploadImage(id, body)
        }
    }
}