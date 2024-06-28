package com.rspk.internproject.ui.screens

import android.content.Context
import android.widget.Toast
import com.rspk.internproject.R
import com.rspk.internproject.crypto.CryptoManager
import com.rspk.internproject.data.PasswordManagerEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

val cryptoManager = CryptoManager()

private val uppercaseLetters = ('A'..'Z')
private val lowercaseLetters = ('a'..'z')
private val digits = ('0'..'9')
private val symbols = "!@#$%^&*()-_=+<>?/".toList()

data class IconData(
    val iconVisible: Int,
    val iconHidden: Int ,
    val contentDescriptionVisible: Int,
    val contentDescriptionHidden: Int ,
    val onClick: (() -> Unit)? = null
){
    companion object {
        fun passwordDefaults(): IconData {
            return IconData(
                iconVisible = R.drawable.baseline_visibility_24,
                iconHidden = R.drawable.baseline_visibility_off_24,
                contentDescriptionVisible = R.string.visibility,
                contentDescriptionHidden = R.string.visibility_off
            )
        }
    }
}

data class TextFieldData(
    val text: String = "",
    val onValueChange: (String) -> Unit = {},
    val hint:String = "",
    val trailingIcon : IconData? = null,
    val isPassword: Boolean = false
)

fun getRandomPassword(length: Int = 12): String {
    val allChars = uppercaseLetters + lowercaseLetters + digits + symbols
    return List(length) { allChars.random() }.joinToString("")
}

fun evaluatePasswordStrength(password: String): String {

    val hasUpperCase = password.any { it.isUpperCase() }
    val hasLowerCase = password.any { it.isLowerCase() }
    val hasDigit = password.any { it.isDigit() }
    val hasSymbol = password.any { "!@#$%^&*()-_=+<>?/".contains(it) }
    val isLongEnough = password.length >= 12

    val criteriaMet = listOf(hasUpperCase, hasLowerCase, hasDigit, hasSymbol, isLongEnough).count { it }

    return when {
        criteriaMet == 5 -> "Password is strong"
        criteriaMet >= 3 -> "Password is ok"
        else -> "Password is too weak"
    }
}

fun checkButtonDetails(
    accountType: String,
    userName: String,
    password: String,
    context: Context,
    bottomSheetViewModel: BottomSheetViewModel,
): Flow<Boolean> {
    return flow {
        if (accountType.isNotEmpty() && userName.isNotEmpty() && password.isNotEmpty()) {
            val encryptedPassword = cryptoManager.encrypt(password)
            val userExists = withContext(Dispatchers.IO) {
                bottomSheetViewModel.checkIfUserExists(accountType, userName) > 0
            }
            if (!userExists) {
                withContext(Dispatchers.IO) {
                    bottomSheetViewModel.addUserDetails(
                        PasswordManagerEntity(
                            accountName = accountType,
                            userEmail = userName,
                            password = encryptedPassword
                        )
                    )
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Addition Successful", Toast.LENGTH_SHORT).show()
                }
                emit(false)
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "User Already Exists", Toast.LENGTH_SHORT).show()
                }
                emit(true)
            }
        } else {
            emit(true)
        }
    }
}

fun updateUserDetails(
    id:Int,
    accountType:String,
    userName:String,
    password:String,
    bottomSheetViewModel: BottomSheetViewModel
){
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    val encryptedPassword = cryptoManager.encrypt(password)

    if(accountType.isNotEmpty() && userName.isNotEmpty() && password.isNotEmpty()){
        coroutineScope.launch {
            bottomSheetViewModel.updateUserDetails(
                PasswordManagerEntity(
                    id = id,
                    accountName = accountType,
                    userEmail = userName,
                    password = encryptedPassword
                )
            )
        }
    }
}

fun deleteUserDetails(
    id:Int,
    accountType:String,
    userName:String,
    password:String,
    bottomSheetViewModel: BottomSheetViewModel
){
    val coroutineScope = CoroutineScope(Dispatchers.IO)

    val encryptedPassword = cryptoManager.encrypt(password)

    coroutineScope.launch {
        bottomSheetViewModel.deleteUserDetails(
            PasswordManagerEntity(
                id = id,
                accountName = accountType,
                userEmail = userName,
                password = encryptedPassword
            )
        )
    }
}

fun decryptPassword(encryptedPassword: ByteArray): String {
    return try {
        encryptedPassword.let {
            val decrypted = cryptoManager.decrypt(encryptedPassword)
            decrypted
        }
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

