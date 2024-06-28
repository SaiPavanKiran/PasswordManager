package com.rspk.internproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PasswordManagerEntity::class] , version = 2)
abstract class ApplicationRoomDatabase: RoomDatabase() {

    abstract fun passwordManagerDaoInstance():PasswordManagerDao

    companion object{
        private var INSTANCE:ApplicationRoomDatabase? = null
        fun getRoomInstance(context: Context):ApplicationRoomDatabase{
            return INSTANCE ?: synchronized(this){
                Room
                    .databaseBuilder(
                        context = context,
                        klass = ApplicationRoomDatabase::class.java,
                        name = "user_accounts_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}