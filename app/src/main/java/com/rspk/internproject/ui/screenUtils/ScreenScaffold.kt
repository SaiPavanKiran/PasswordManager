package com.rspk.internproject.ui.screenUtils

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rspk.internproject.R
import com.rspk.internproject.ui.navigation.NavRoutes
import com.rspk.internproject.ui.navigation.Navigation
import com.rspk.internproject.ui.screens.AddBottomSheet
import com.rspk.internproject.ui.screens.ViewDetailsBottomSheet

val LocalNavController = compositionLocalOf<NavController> { error("navController not found") }
val LocalIsBottomSheetVisible = compositionLocalOf<Boolean> { error("bottomSheetVisible not found") }
val LocalOnBottomStateChanged = compositionLocalOf<((Boolean) -> Unit)> { error("bottomSheetVisible not found") }
val LocalCurrentIndex = compositionLocalOf<Int?> { error("bottomSheetVisible not found") }
val LocalOnCurrentIndexChanged = compositionLocalOf<((Int?) -> Unit)> { error("bottomSheetVisible not found") }
@Composable
fun LocalValuesProvider(){
    val navController = rememberNavController()
    var currentLazyListIndex by rememberSaveable { mutableStateOf<Int?>(null) }
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val currentIndexChanged = { value:Int? -> currentLazyListIndex = value }
    val onBottomStateChanged: (Boolean) -> Unit = { value:Boolean -> isBottomSheetVisible = value }
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    CompositionLocalProvider(
        LocalNavController provides navController,
        LocalIsBottomSheetVisible provides isBottomSheetVisible,
        LocalOnBottomStateChanged provides onBottomStateChanged,
        LocalCurrentIndex provides currentLazyListIndex,
        LocalOnCurrentIndexChanged provides currentIndexChanged
    ) {
        ScreenScaffold(
            currentRoute = currentRoute
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenScaffold(
    currentRoute: String?,
    bottomSheetVisible:Boolean = LocalIsBottomSheetVisible.current ,
    onBottomStateChanged:(Boolean) -> Unit = LocalOnBottomStateChanged.current,
    currentLazyListIndex:Int? = LocalCurrentIndex.current,
){
    val titleName = when(currentRoute){
        "home" -> R.string.app_name
        else -> R.string.app_name
    }
    Scaffold(
        topBar = {
            TopBar(
                title = titleName,
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        },
        floatingActionButton = {
            FloatingButton(
                onClick = {
                    if(!bottomSheetVisible){
                        onBottomStateChanged(true)
                    }
                },
                icon = R.drawable.baseline_add_24,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_medium))
            )
        },
    ) {
        Navigation(
            modifier = Modifier
                .padding(it)
        )
        if(bottomSheetVisible && currentLazyListIndex == null){
            AddBottomSheet()
        }
        if(bottomSheetVisible && currentLazyListIndex != null){
            ViewDetailsBottomSheet(currentLazyListIndex = currentLazyListIndex)
        }
    }
}