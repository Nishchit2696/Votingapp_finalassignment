package com.example.voting.Dao

import androidx.room.*
import com.example.voting.entity.User


@Dao
interface UserDAO {
    @Insert
    suspend fun registerUser(user: User)

   // @Query("select * from User where ctz = (:citizenship) and psw = (:password)")
    suspend fun loginUser(citizenship : String, password : String) : User

    @Delete
    suspend fun deleteUser(user: User)
    @Update
    suspend fun updateUser(user: User)
}

