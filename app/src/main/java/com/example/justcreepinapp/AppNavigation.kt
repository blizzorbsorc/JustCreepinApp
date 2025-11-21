package com.example.justcreepinapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavigation(navController: NavController, modifier: Modifier = Modifier) {
    val appViewModel: AppViewModel = viewModel()
    NavHost(
        navController = navController as NavHostController,
        startDestination = "home_screen",
        //startDestination = "splash_screen",
        modifier = modifier
    ) {
        composable("splash_screen") {
            SplashScreen(onTimeout = {
                navController.navigate("home_screen") {
                    popUpTo("splash_screen") { inclusive = true }
                }
            })
        }
        composable("home_screen"){
            //val appViewModel: AppViewModel = viewModel()
            HomeScreen(
                viewModel = appViewModel,
                onHolidayClick = {
                    navController.navigate(("list_screen"))
                }
            )
            //HomeScreen()
        }
        composable("list_screen"){
            ListScreen(
                viewModel = appViewModel,
                onBackClick = {
                    navController.navigate("home_screen")
                },
                onAddLocationClick = {
                    navController.navigate("detail_screen")
                },
                onLocationClick = { location ->
                    navController.navigate("detail_screen/${location.id}")
                }
            )
        }
        composable("detail_screen"){
            DetailScreen(
                viewModel = appViewModel,
                locationId = null,
                onBackClick = {navController.popBackStack()}
            )
        }
        composable("detail_screen/{locationId"){backstackEntry ->
            val locationId = backstackEntry.arguments?.getString("locationId")?.toIntOrNull()
            val location = appViewModel.locations.value.firstOrNull{it.id==locationId}
            if(location!=null) {
                DetailScreen(
                    viewModel = appViewModel,
                    locationId = location.id,
                    onBackClick = {navController.popBackStack()}
                )
            }
            /*val id = backstackEntry.arguments?.getString("locationId")?.toIntOrNull()
            DetailScreen(
                viewModel = appViewModel,
                locationId = id,
                onBackClick = {navController.popBackStack()}
            )*/
        }
    }
}