package com.example.voting.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update


@Dao
interface UserDAO {
    @Insert
    suspend fun registerUser(user: User)
    @Delete
    suspend fun deleteUser(user: User)
    @Update
    suspend fun updateUser(user: User)
}

}