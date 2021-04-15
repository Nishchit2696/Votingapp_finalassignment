package com.example.voting.api

import com.example.voting.entity.License
import com.example.voting.response.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface LicenseAPI {
    @POST("license_insert_app")
    suspend fun insertLicense(
            @Header("Authorization") token : String,
            @Body license: License
    ): Response<LicenseResponse>

    //get all students
    @GET("license/fetchall")
    suspend fun getlicense(
            @Header("Authorization") token : String,
    ): Response<AllLicenseResponse>

    //delete student

    @DELETE("license/delete/{id}")
    suspend fun deletelicense(
            @Header("Authorization") token: String,
            @Path("id") id: String
    ): Response<DeleteLicenseResponse>

    @Multipart
    @PUT("Limage/update/{id}")
    suspend fun uploadLImage(
            @Path("id") id: String,
            @Part file: MultipartBody.Part
    ): Response<ImageResponse>

}