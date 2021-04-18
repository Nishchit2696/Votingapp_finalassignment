package com.example.voting.repository

import com.example.voting.api.LicenseAPI
import com.example.voting.api.MyApiRequest
import com.example.voting.api.ServiceBuilder
import com.example.voting.entity.License
import com.example.voting.entity.Passport
import com.example.voting.response.*
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

    suspend fun getlicense(): AllLicenseResponse {
        return apiRequest {
            licenseAPI.getlicense(
                    ServiceBuilder.token!!
            )
        }
    }

    suspend fun updatelicense(id:String, license: License): UdateLicenseResponse {
        return apiRequest {
            licenseAPI.updatelicense(
                ServiceBuilder.token!! ,id, license
            )
        }
    }

    suspend fun Deletelicense(license: String): DeleteLicenseResponse {
        return apiRequest {
            licenseAPI.deletelicense(
                    ServiceBuilder.token!!,license
            )
        }
    }


    suspend fun uploadImage(id: String, body: MultipartBody.Part)
            : ImageResponse {
        return apiRequest {
            licenseAPI.uploadLImage(id, body)
        }
    }
}