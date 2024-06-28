package com.rspk.internproject.data

import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun insertUserDetails(passwordManagerEntity: PasswordManagerEntity)

    suspend fun updateUserDetails(passwordManagerEntity: PasswordManagerEntity)

    suspend fun deleteUserDetails(passwordManagerEntity: PasswordManagerEntity)

    fun getEntireList(): Flow<List<PasswordManagerEntity>>

    suspend fun getParticularUserDetail(id:Int) : PasswordManagerEntity

    suspend fun checkIfUserExists(accountName:String,userName:String): Int
}