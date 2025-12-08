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
        modifier = modifier
    ) {
        // splash screen
        composable("splash_screen") {
            SplashScreen(onTimeout = {
                navController.navigate("home_screen") {
                    popUpTo("splash_screen") { inclusive = true }
                }
            })
        }
        // home screen
        composable("home_screen"){
            HomeScreen(
                viewModel = appViewModel,
                onHolidayClick = {
                    navController.navigate(("map_screen"))
                }
            )
        }
        // list screen
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
        // detail screen
        composable("detail_screen"){
            DetailScreen(
                viewModel = appViewModel,
                locationId = null,
                onBackClick = {navController.popBackStack()}
            )
        }
        // detail screen with locationId
        composable("detail_screen/{locationId}"){backstackEntry ->

            val locationId = backstackEntry.arguments?.getString("locationId")?.toIntOrNull()
            DetailScreen(
                viewModel = appViewModel,
                locationId = locationId,
                onBackClick = {navController.popBackStack()}
            )

        }
        // map screen
        composable("map_screen"){
            MapScreen(modifier = Modifier, appViewModel = appViewModel, onMarkerClick = {locationId ->
                navController.navigate("detail_screen/$locationId")
            })
        }
    }
}