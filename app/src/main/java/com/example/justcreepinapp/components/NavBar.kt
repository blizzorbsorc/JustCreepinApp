package com.example.justcreepinapp.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.justcreepinapp.R

// Define a navigation bar
@Composable
fun NavBar(navController: NavController) {
    val items = listOf("home_screen", "map_screen", "list_screen")
    val icons = listOf(Icons.Default.Home, Icons.Filled.Map, Icons.AutoMirrored.Filled.List)
    val labels = listOf(stringResource(R.string.home), stringResource(R.string.map),
        stringResource(R.string.list)
    )

    // Add a navigation item for each item in items
    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState().value
        val currentRoute = navBackStackEntry?.destination?.route

        // Add a navigation item for each item in items
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = currentRoute == item,
                onClick = { navController.navigate(item) },
                icon = { Icon(icons[index], contentDescription = labels[index]) },
                label = { Text(labels[index]) }
            )
        }
    }
}