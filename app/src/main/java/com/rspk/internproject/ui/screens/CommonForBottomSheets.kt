package com.rspk.internproject.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.rspk.internproject.R

@Composable
fun BottomSheetContent(
    modifier: Modifier = Modifier,
    textFieldsList: List<TextFieldData>,
    buttonContent:@Composable () -> Unit,
    checkBlankFieldsValue: Boolean,
    readOnly: Boolean = false,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
    ){
    var showIcon by rememberSaveable { mutableStateOf(true) }
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = dimensionResource(id = R.dimen.sheet_padding_horizontal),
                vertical = dimensionResource(id = R.dimen.sheet_padding_vertical)
            )
    ) {
        textFieldsList.forEach {
            OutlinedTextField(
                value = it.text,
                onValueChange = it.onValueChange,
                label = { Text(text = it.hint) },
                supportingText = {
                    if(it.text.isEmpty() && checkBlankFieldsValue){
                        Text(
                            text = stringResource(id = R.string.required_field),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    if(it.isPassword && it.text.isNotEmpty()){
                        Text(
                            text = "*${evaluatePasswordStrength(password = it.text)}"
                        )
                    }
                },
                trailingIcon = {
                    it.trailingIcon?.let{ iconData ->
                        IconButton(onClick = {
                            showIcon = !showIcon
                        }) {
                            Icon(
                                painter = painterResource(id = if(showIcon) iconData.iconHidden else iconData.iconVisible),
                                contentDescription = stringResource(id = if(showIcon) iconData.contentDescriptionHidden else iconData.contentDescriptionVisible )
                            )
                        }
                    }
                },
                readOnly = readOnly,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.textField_shape)),
                visualTransformation = if(it.isPassword && showIcon){
                    PasswordVisualTransformation('\u002A')
                }else{
                    VisualTransformation.None
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (it.isPassword) KeyboardType.Password else KeyboardType.Text
                ),
                colors = colors,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        buttonContent()
    }
}

@Composable
fun SheetButton(
    modifier: Modifier = Modifier,
    @StringRes buttonTitle:Int,
    onClick:() -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors()
){
    Button(
        colors = colors,
        onClick = {
            onClick()
        },
        modifier = modifier
            .padding(
                vertical = dimensionResource(id = R.dimen.sheet_padding_vertical),
            )
    ) {
        Text(
            text = stringResource(id = buttonTitle),
            modifier = Modifier
                .padding(
                    vertical = dimensionResource(id = R.dimen.button_padding),
                    horizontal = dimensionResource(id = R.dimen.sheet_padding_horizontal),
                )
        )
    }
}