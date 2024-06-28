package com.rspk.internproject.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rspk.internproject.ApplicationViewModelProvider
import com.rspk.internproject.R
import com.rspk.internproject.ui.screenUtils.LocalOnBottomStateChanged

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
            .height((LocalConfiguration.current.screenHeightDp/2).dp)
    ) {
        BottomSheetContent(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainerLow),
            textFieldsList = textFieldsList,
            checkBlankFieldsValue = checkBlankFieldsValue,
            buttonContent = {
                SheetButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSurface),
                    buttonTitle = R.string.add_button_title,
                    onClick = {
                        checkBlankFieldsValue = true
                        checkButtonDetails(
                            accountType = accountTypeText,
                            userName = userNameText,
                            password = passwordText,
                            context = context,
                            bottomSheetViewModel = bottomSheetViewModel
                        )
                        bottomSheetState(false)
                    }
                )
            }
        )
    }
}
