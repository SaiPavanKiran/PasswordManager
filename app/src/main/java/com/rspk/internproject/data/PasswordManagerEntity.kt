package com.rspk.internproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userAccounts")
data class PasswordManagerEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val accountName : String,
    val userEmail : String,
    val password : ByteArray,
)