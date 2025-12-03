package com.example.justcreepinapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.justcreepinapp.components.NavBar
import com.example.justcreepinapp.ui.theme.JustCreepinAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JustCreepinAppTheme {
                var showSplash by remember { mutableStateOf(true) }

                if (showSplash) {
                    SplashScreen(onTimeout = { showSplash = false })
                } else {
                    val navController = rememberNavController()
                    /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        AppNavigation(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }*/

                    // Keep track of the current route
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    // Define listOf routes that show NavBar
                    val routesWithNavBar = listOf("map_screen", "list_screen", "detail_screen")

                    // Check if currentRoute is in listOf routes that show NavBar
                    val shouldShowNavBar = routesWithNavBar.any { currentRoute?.startsWith(it) == true }

                    Scaffold(
                        bottomBar = { if (shouldShowNavBar) NavBar(navController = navController) }

                    ) {
                            innerPadding ->
                        AppNavigation(navController = navController, modifier = Modifier.padding(innerPadding))
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JustCreepinAppTheme {
        Greeting("Android")
    }
}