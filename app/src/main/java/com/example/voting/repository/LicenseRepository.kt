package com.example.voting.repository

import com.example.voting.api.LicenseAPI
import com.example.voting.api.MyApiRequest
import com.example.voting.api.ServiceBuilder
import com.example.voting.entity.License
import com.example.voting.response.AllLicenseResponse
import com.example.voting.response.DeleteLicenseResponse
import com.example.voting.response.ImageResponse
import com.example.voting.response.LicenseResponse
import okhttp3.MultipartBody

class LicenseRepository
    : MyApiRequest() {

    private val licenseAPI =
            ServiceBuilder.buildService(LicenseAPI::class.java)

    //Add Student

    suspend fun insertLicense(license: License): LicenseResponse {
        return apiRequest {
            licenseAPI.insertLicense( ServiceBuilder.token!!, license)
        }
    }

//    suspend fun getlicense(): AllLicenseResponse {
//        return apiRequest {
//            licenseAPI.getlicense(
//                    ServiceBuilder.token!!
//            )
//        }
//    }
//
//    suspend fun deletepassport(passport: String): DeleteLicenseResponse {
//        return apiRequest {
//            licenseAPI.deletelicense(
//                    ServiceBuilder.token!!,passport
//            )
//        }
//    }


    suspend fun uploadImage(id: String, body: MultipartBody.Part)
            : ImageResponse {
        return apiRequest {
            licenseAPI.uploadLImage(id, body)
        }
    }
}