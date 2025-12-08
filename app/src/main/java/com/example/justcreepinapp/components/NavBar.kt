package com.example.justcreepinapp.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

// Define a navigation bar
@Composable
fun NavBar(navController: NavController) {
    val items = listOf("home_screen", "map_screen", "list_screen")
    val icons = listOf(Icons.Default.Home, Icons.Filled.Map, Icons.Filled.List)
    val labels = listOf("Home", "Map", "List")

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