package com.rspk.internproject.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rspk.internproject.data.PasswordManagerEntity
import com.rspk.internproject.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class GetParticularUserDetail(
    val particularUserDetail: PasswordManagerEntity? = null
)
class BottomSheetViewModel(
    private val repositoryInstance: Repository
):ViewModel(){
    private val _userDetailState = MutableStateFlow(GetParticularUserDetail())
    val userDetailState: StateFlow<GetParticularUserDetail> = _userDetailState.asStateFlow()

    suspend fun checkIfUserExists(accountName:String,userName:String):Int{
        return repositoryInstance.checkIfUserExists(accountName,userName)
    }


    fun getParticularUserDetail(id: Int) {
        viewModelScope.launch {
            val userDetail = repositoryInstance.getParticularUserDetail(id)
            _userDetailState.value = GetParticularUserDetail(particularUserDetail = userDetail)
        }
    }

    fun addUserDetails(passwordManagerEntity: PasswordManagerEntity){
        viewModelScope.launch {
            repositoryInstance.insertUserDetails(passwordManagerEntity)
        }
    }

    fun updateUserDetails(passwordManagerEntity: PasswordManagerEntity){
        viewModelScope.launch {
            repositoryInstance.updateUserDetails(passwordManagerEntity)
        }
    }

    fun deleteUserDetails(passwordManagerEntity: PasswordManagerEntity){
        viewModelScope.launch {
            repositoryInstance.deleteUserDetails(passwordManagerEntity)
        }
    }
}