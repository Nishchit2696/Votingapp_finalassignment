package com.example.voting.Userdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.voting.Dao.UserDAO
import com.example.voting.entity.User

@Database(
        entities = [(User::class)],
        version = 1
)
abstract class UserDB: RoomDatabase() {
    abstract fun getUserDao() : UserDAO

    companion object{
        @Volatile
        private var instance : UserDB? = null
        fun getInstance(context : Context): UserDB{
            if(instance==null){
                synchronized(UserDB::class){
                    instance = buildDatabase(context)
                }
            }
            return instance!!
        }
        private fun buildDatabase(context : Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        UserDB::class.java,
                        "UserDb"
                ).build()
    }
}
