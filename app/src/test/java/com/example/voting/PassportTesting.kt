package com.example.voting

import com.example.voting.api.ServiceBuilder
import com.example.voting.entity.License
import com.example.voting.entity.Passport
import com.example.voting.entity.User
import com.example.voting.repository.LicenseRepository
import com.example.voting.repository.PassportRepository
import com.example.voting.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class PassportTesting {
    private lateinit var LicenseRepository: LicenseRepository
    private lateinit var PassportRepository: PassportRepository
    private lateinit var userRepository : UserRepository

    @Test
    fun checklogin()= runBlocking {
        userRepository = UserRepository()
        val response = userRepository.checkUser("567","renish")
        val expactedResult = true
        val actualResult = response.success
        Assert.assertEquals(expactedResult, actualResult)
    }

    @Test
    fun checkregister() = runBlocking {
        val user = User( Firstname = "Anish Nepal", Lastname = "anish10", Email = "anish",
            Citizenship = "21", Phonenumber = "23", Password = "renish"  )
        userRepository = UserRepository()
        val response = userRepository.registerUser(user)
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun checkpassport() = runBlocking {
        userRepository = UserRepository()
        ServiceBuilder.token = "Bearer " + userRepository.checkUser("567", "renish").token

        val passport = Passport(_id = "1",simage = null, Firstname = "Anish Nepal", Lastname = "anish10", Fathername = "anish",
            CitizenshipNo = "21", Ocupation = "23", Education = "renish", Phonenumber = "98765" )

        PassportRepository = PassportRepository()
        val response = PassportRepository.insertPassport(passport)
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

}