package com.rspk.internproject

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rspk.internproject.ui.screens.BottomSheetViewModel
import com.rspk.internproject.ui.screens.HomeScreenViewModel

object ApplicationViewModelProvider {
    val Factory:ViewModelProvider.Factory by lazy {
        viewModelFactory {
            initializer {
                HomeScreenViewModel(currentApplicationInstance().container.repositoryInstance)
            }
            initializer {
                BottomSheetViewModel(currentApplicationInstance().container.repositoryInstance)
            }
        }
    }
}


fun CreationExtras.currentApplicationInstance(): ApplicationClass =
    (this[APPLICATION_KEY] as ApplicationClass)