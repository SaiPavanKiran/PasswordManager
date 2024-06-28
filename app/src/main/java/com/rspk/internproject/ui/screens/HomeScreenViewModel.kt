package com.rspk.internproject.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rspk.internproject.data.PasswordManagerEntity
import com.rspk.internproject.data.Repository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class GetEntireList(
    val entireList: List<PasswordManagerEntity> = emptyList()
)

class HomeScreenViewModel(
    repositoryInstance: Repository
) :ViewModel(){

    val entireList =
        repositoryInstance
            .getEntireList()
            .map{ GetEntireList(entireList = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = GetEntireList()
            )
}