package com.rspk.internproject.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rspk.internproject.ApplicationViewModelProvider
import com.rspk.internproject.R
import com.rspk.internproject.ui.screenUtils.LocalOnBottomStateChanged
import com.rspk.internproject.ui.screenUtils.LocalOnCurrentIndexChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewDetailsBottomSheet(
    modifier: Modifier = Modifier,
    currentLazyListIndex:Int,
    onBottomStateChanged: (Boolean) -> Unit = LocalOnBottomStateChanged.current,
    currentLazyListIndexChange: (Int?) -> Unit = LocalOnCurrentIndexChanged.current,
    bottomSheetViewModel: BottomSheetViewModel = viewModel(factory = ApplicationViewModelProvider.Factory)
){
    val userDetailsState by bottomSheetViewModel.userDetailState.collectAsState()
    val userDetails = userDetailsState.particularUserDetail
    var accountTypeText by rememberSaveable { mutableStateOf("") }
    var userNameText by rememberSaveable { mutableStateOf("") }
    var passwordText by rememberSaveable { mutableStateOf("") }
    var readOnly by rememberSaveable { mutableStateOf(true) }
    val checkBlankFieldsValue by rememberSaveable { mutableStateOf(true) }
    val readOnlyChanged: (Boolean) -> Unit = { value:Boolean -> readOnly = value }

    LaunchedEffect(currentLazyListIndex) {
        bottomSheetViewModel.getParticularUserDetail(currentLazyListIndex)
    }

    LaunchedEffect(userDetails) {
        accountTypeText = userDetails?.accountName ?: ""
        userNameText = userDetails?.userEmail ?: ""
        passwordText = userDetails?.password?.let {
            decryptPassword(it)
        } ?: ""
    }

    val textFieldsList = listOf(
        TextFieldData(text = accountTypeText, onValueChange =  { accountTypeText = it }, hint = "Account Name"),
        TextFieldData(text = userNameText, onValueChange = { userNameText = it }, hint =  "Username/Email"),
        TextFieldData(text = passwordText, onValueChange = { passwordText = it }, hint = "Password" , trailingIcon = IconData.passwordDefaults(), isPassword = true)
    )

    ModalBottomSheet(
        onDismissRequest = {
            onBottomStateChanged(false)
            currentLazyListIndexChange(null)
        },
        modifier = modifier
            .height((LocalConfiguration.current.screenHeightDp / 2).dp)
    ) {
        BottomSheetContent(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .verticalScroll(rememberScrollState()),
            textFieldsList = textFieldsList,
            checkBlankFieldsValue = checkBlankFieldsValue,
            readOnly = readOnly,
            colors = if (readOnly) {
                OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent
                )
            } else {
                OutlinedTextFieldDefaults.colors()
            },
            buttonContent = {
                BottomSheetButtonForViewDetails(
                    readOnly = readOnly,
                    readOnlyChanged = readOnlyChanged,
                    currentLazyListIndex = currentLazyListIndex,
                    accountTypeText = accountTypeText,
                    userNameText = userNameText,
                    passwordText = passwordText,
                    bottomSheetViewModel = bottomSheetViewModel
                )
            }
        )
    }
}

@Composable
fun BottomSheetButtonForViewDetails(
    modifier: Modifier = Modifier,
    readOnly: Boolean,
    readOnlyChanged: (Boolean) -> Unit,
    currentLazyListIndex: Int,
    accountTypeText: String,
    userNameText: String,
    passwordText: String,
    bottomSheetViewModel: BottomSheetViewModel,
){
    val bottomSheetState = LocalOnBottomStateChanged.current
    val currentStoredIndex = LocalOnCurrentIndexChanged.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxWidth()
    ){
        SheetButton(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSurface),
            buttonTitle = if(readOnly) R.string.edit_button_title else R.string.update_button_title,
            onClick = {
                readOnlyChanged(!readOnly)
                updateUserDetails(
                    id = currentLazyListIndex,
                    accountType = accountTypeText,
                    userName = userNameText,
                    password = passwordText,
                    bottomSheetViewModel = bottomSheetViewModel
                )
            }
        )
        SheetButton(
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            buttonTitle = R.string.delete_button_title,
            onClick = {
                deleteUserDetails(
                    id = currentLazyListIndex,
                    accountType = accountTypeText,
                    userName = userNameText,
                    password = passwordText,
                    bottomSheetViewModel = bottomSheetViewModel
                )
                bottomSheetState(false)
                currentStoredIndex(null)
            }
        )
    }
}
