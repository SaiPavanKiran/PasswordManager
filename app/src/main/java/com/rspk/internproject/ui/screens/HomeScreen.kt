package com.rspk.internproject.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rspk.internproject.ApplicationViewModelProvider
import com.rspk.internproject.R
import com.rspk.internproject.ui.screenUtils.LocalOnBottomStateChanged
import com.rspk.internproject.ui.screenUtils.LocalOnCurrentIndexChanged
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel = viewModel(factory = ApplicationViewModelProvider.Factory)
) {
    val userDetailsList = homeScreenViewModel.entireList.collectAsState().value.entireList
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            vertical =  dimensionResource(id = R.dimen.card_padding_vertical)
        )
    ) {
        items(userDetailsList, key = { it.id }) {
            HomeScreenItems(
                accountName = it.accountName,
                userPassword = it.password.toString(),
                id= it.id,
                icon = R.drawable.baseline_arrow_forward_ios_24
            )
        }
    }
}


@Composable
fun HomeScreenItems(
    modifier: Modifier = Modifier,
    cardModifier: Modifier = Modifier,
    id:Int,
    accountName: String,
    userPassword: String,
    @DrawableRes icon: Int? = null
){
    val bottomSheetValueChanged = LocalOnBottomStateChanged.current
    val currentIndexChange = LocalOnCurrentIndexChanged.current
    val scope = rememberCoroutineScope()
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = cardModifier
            .padding(
                vertical = dimensionResource(id = R.dimen.card_padding_vertical),
                horizontal = dimensionResource(id = R.dimen.card_padding_horizontal)
            )
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.card_shape)))
            .clickable {
                scope.launch {
                    bottomSheetValueChanged(true)
                }
                scope.launch {
                    currentIndexChange(id)
                }
            }
    ) {
        CardContent(
            accountName = accountName,
            userPassword = userPassword,
            modifier = modifier,
            icon = icon
        )
    }
}


@Composable
fun CardContent(
    modifier: Modifier = Modifier,
    accountName: String,
    userPassword: String,
    @DrawableRes icon: Int? = null
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.card_internal_padding))
    ){
        Text(
            text = accountName,
            style = TextStyle(
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.text_padding_horizontal)
                )
        )
        BasicTextField(
            value = userPassword,
            onValueChange = {},
            visualTransformation = PasswordVisualTransformation('\u002A'),
            readOnly = true,
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        )
        if(icon!=null){
            Icon(
                painter = painterResource(id = icon),
                contentDescription = accountName,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.BottomEnd)
            )
        }
    }
}