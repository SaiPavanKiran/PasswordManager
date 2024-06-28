package com.rspk.internproject.ui.screens

import android.content.Context
import android.widget.Toast
import com.rspk.internproject.R
import com.rspk.internproject.crypto.CryptoManager
import com.rspk.internproject.data.PasswordManagerEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

val cryptoManager = CryptoManager()
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

fun checkButtonDetails(
    accountType:String,
    userName:String,
    password:String,
    context: Context,
    bottomSheetViewModel: BottomSheetViewModel
){
    val coroutineScope = CoroutineScope(Dispatchers.IO)

    val encryptedPassword = cryptoManager.encrypt(password)

    if(accountType.isNotEmpty() && userName.isNotEmpty() && password.isNotEmpty()){
        coroutineScope.launch {
            if(bottomSheetViewModel.checkIfUserExists(accountType,userName) < 1){
                bottomSheetViewModel.addUserDetails(
                    PasswordManagerEntity(
                        accountName = accountType,
                        userEmail = userName,
                        password = encryptedPassword
                    )
                )
            }else{
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "User Already Exists", Toast.LENGTH_SHORT).show()
                }
            }
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

