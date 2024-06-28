package com.rspk.internproject.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordManagerDao {

    @Insert
    suspend fun insertUserDetails(passwordManagerEntity: PasswordManagerEntity)

    @Delete
    suspend fun deleteUserDetails(passwordManagerEntity: PasswordManagerEntity)

    @Update
    suspend fun updateUserDetails(passwordManagerEntity: PasswordManagerEntity)

    @Query("SELECT * FROM userAccounts ORDER BY accountName ASC")
    fun getEntireList():Flow<List<PasswordManagerEntity>>

    @Query("SELECT * FROM userAccounts WHERE id=:id")
    suspend fun getSpecificUserDetail(id:Int):PasswordManagerEntity

    @Query("SELECT COUNT(*) FROM userAccounts WHERE accountName=:accountName AND userEmail =:userName ")
    suspend fun checkIfUserExists(accountName:String,userName:String):Int
}