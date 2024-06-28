package com.rspk.internproject.data

import kotlinx.coroutines.flow.Flow

class PasswordManagerRepository(
    private val passwordManagerDao: PasswordManagerDao
) : Repository {
    override suspend fun insertUserDetails(passwordManagerEntity: PasswordManagerEntity) =
        passwordManagerDao.insertUserDetails(passwordManagerEntity)

    override suspend fun updateUserDetails(passwordManagerEntity: PasswordManagerEntity) =
        passwordManagerDao.updateUserDetails(passwordManagerEntity)

    override suspend fun deleteUserDetails(passwordManagerEntity: PasswordManagerEntity) =
        passwordManagerDao.deleteUserDetails(passwordManagerEntity)

    override fun getEntireList(): Flow<List<PasswordManagerEntity>> =
        passwordManagerDao.getEntireList()

    override suspend fun getParticularUserDetail(id: Int): PasswordManagerEntity =
        passwordManagerDao.getSpecificUserDetail(id)

    override suspend fun checkIfUserExists(accountName: String, userName: String): Int =
        passwordManagerDao.checkIfUserExists(accountName,userName)
}