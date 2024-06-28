package com.rspk.internproject.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rspk.internproject.ApplicationViewModelProvider
import com.rspk.internproject.R
import com.rspk.internproject.ui.screenUtils.LocalOnBottomStateChanged
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBottomSheet(
    modifier: Modifier = Modifier,
    onBottomStateChanged: (Boolean) -> Unit = LocalOnBottomStateChanged.current,
    bottomSheetViewModel: BottomSheetViewModel = viewModel(factory = ApplicationViewModelProvider.Factory)
){
    val bottomSheetState = LocalOnBottomStateChanged.current
    val context = LocalContext.current
    var accountTypeText by rememberSaveable { mutableStateOf("") }
    var userNameText by rememberSaveable { mutableStateOf("") }
    var passwordText by rememberSaveable { mutableStateOf("") }
    var checkBlankFieldsValue by rememberSaveable { mutableStateOf(false) }
    val checkBlankFieldsChanged: (Boolean) -> Unit = { value:Boolean -> checkBlankFieldsValue = value }

    val textFieldsList = listOf(
        TextFieldData(text = accountTypeText, onValueChange =  { accountTypeText = it }, hint = "Account Name"),
        TextFieldData(text = userNameText, onValueChange = { userNameText = it }, hint =  "Username/Email"),
        TextFieldData(text = passwordText, onValueChange = { passwordText = it }, hint = "Password" , trailingIcon = IconData.passwordDefaults(), isPassword = true)
    )

    ModalBottomSheet(
        onDismissRequest = {
            onBottomStateChanged(false)
        },
        modifier = modifier
            .height((2*(LocalConfiguration.current.screenHeightDp/3)).dp)
    ) {
        BottomSheetContent(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .verticalScroll(rememberScrollState()),
            textFieldsList = textFieldsList,
            checkBlankFieldsValue = checkBlankFieldsValue,
            buttonContent = {
                BottomSheetButtonForAddPage(
                    accountTypeText = accountTypeText,
                    userNameText = userNameText,
                    passwordText = passwordText,
                    bottomSheetViewModel = bottomSheetViewModel,
                    checkBlankFieldsChanged = checkBlankFieldsChanged,
                    context = context,
                    onPasswordChanged = { passwordText = it }
                )
            }
        )
    }
}

@Composable
fun BottomSheetButtonForAddPage(
    modifier: Modifier = Modifier,
    context: Context,
    checkBlankFieldsChanged: (Boolean) -> Unit,
    onPasswordChanged : (String) -> Unit,
    accountTypeText: String,
    userNameText: String,
    passwordText: String,
    bottomSheetViewModel: BottomSheetViewModel,
){
    val scope = rememberCoroutineScope()
    val bottomSheetStateChange = LocalOnBottomStateChanged.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxWidth()
    ){
        SheetButton(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSurface),
            buttonTitle = R.string.random_password_button_title,
            onClick = {
                onPasswordChanged(getRandomPassword())
            }
        )
        SheetButton(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSurface),
            buttonTitle = R.string.add_button_title,
            onClick = {
                checkBlankFieldsChanged(true)
                scope.launch {
                    checkButtonDetails(
                        accountType = accountTypeText,
                        userName = userNameText,
                        password = passwordText,
                        context = context,
                        bottomSheetViewModel = bottomSheetViewModel
                    ).collect{
                        bottomSheetStateChange(it)
                    }
                }
            }
        )
    }
}
