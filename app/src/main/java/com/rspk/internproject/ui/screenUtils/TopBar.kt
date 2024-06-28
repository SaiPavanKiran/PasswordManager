package com.rspk.internproject.ui.screenUtils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import com.rspk.internproject.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    imageModifier:Modifier = Modifier,
    @StringRes title:Int,
    style : TextStyle = MaterialTheme.typography.titleLarge,
    fontSize : TextUnit = TextUnit.Unspecified,
    @DrawableRes navigationIcon:Int? = null,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    onNavigationIconClick: () -> Unit = {},
    ) {
    TopAppBar(
        title = { 
            Text(
                text = stringResource(id = title),
                style = style,
                fontSize = fontSize,
                )
        },
        navigationIcon = {
            if(navigationIcon != null){
                Icon(
                    painter = painterResource(id = navigationIcon),
                    contentDescription = stringResource(id = R.string.navigation_icon,title),
                    modifier = imageModifier
                        .clickable {
                            onNavigationIconClick()
                        }
                )
            }
        },
        actions = {},
        colors = colors,
        modifier = modifier
        )
}