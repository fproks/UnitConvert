package com.example.unitconvert.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.unitconvert.R
import com.example.unitconvert.ui.theme.UnitConvertTheme
import kotlinx.coroutines.launch

@Preview
@Composable
fun ComposeUnitConverter() {
    val navController = rememberNavController()
    val mentItens = listOf("Item #1", "Item #2")
    val scaffoldState = rememberScaffoldState()
    val sncakbarCoroutineScope = rememberCoroutineScope()  //协程

    UnitConvertTheme {
        Scaffold(scaffoldState = scaffoldState,
            topBar = {
                ComposeUnitConverterTopBar(mentItens) { s ->
                    sncakbarCoroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(s)
                    }
                }
            },
            bottomBar = { ComposeUnitConverterBottomBar(navController) }
        ) {

            ComposeUnitConverterNavHost(
                navController = navController,
                modifier = Modifier.padding(it)
            )
        }
    }


}

@Preview
@Composable
fun TopBarPreview() {
    ComposeUnitConverterTopBar(listOf("item1 #1", "item2 #2")) {  }
}

@Composable
fun ComposeUnitConverterTopBar(mentunItems: List<String>, onClick: (String) -> Unit) {
    var menuOpened by remember { mutableStateOf(false) }
    TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
            Box {
                IconButton(onClick = {
                    menuOpened = true
                }) {
                    Icon(Icons.Default.MoreVert, "")
                }
                DropdownMenu(expanded = menuOpened,
                    onDismissRequest = { menuOpened = false }) {
                    mentunItems.forEachIndexed { index, s ->
                        if (index > 0) Divider()
                        DropdownMenuItem(onClick = {
                            menuOpened = false
                            onClick(s)
                        }) { Text(s) }
                    }
                }
            }
        })

}

@Composable
fun ComposeUnitConverterBottomBar(navController: NavHostController) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        ComposeUnitConverterScreen.screens.forEach { screen ->
            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = { navController.navigate(screen.route) { launchSingleTop = true } },
                label = { Text(text = stringResource(id = screen.label)) },
                icon = { Icon(painterResource(id = screen.icon), stringResource(id = screen.label)) },
                alwaysShowLabel = false
            )
        }

    }
}


@Composable
fun ComposeUnitConverterNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = ComposeUnitConverterScreen.route_temperature,
        modifier = modifier
    ) {
        composable(ComposeUnitConverterScreen.route_temperature) {
            TemperatureConverter()
        }
        composable(ComposeUnitConverterScreen.route_distances) {
            DistanceConverter()
        }
    }
}