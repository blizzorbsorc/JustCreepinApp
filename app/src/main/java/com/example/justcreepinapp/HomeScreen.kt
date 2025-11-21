package com.example.justcreepinapp

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(viewModel: AppViewModel, onHolidayClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                Spacer(Modifier.height(48.dp))
                Button(
                    onClick = {
                        //viewModel.skin.value = "Halloween"
                        viewModel.loadLocations("Halloween")
                        onHolidayClick()
                    }
                ) {
                    //Text("Halloween")
                    Text(stringResource(R.string.button_halloween))
                }
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = {
                        //viewModel.skin.value = "Christmas"
                        viewModel.loadLocations("Christmas")
                        onHolidayClick()
                    }
                ) {
                    //Text("Christmas")
                    Text(stringResource(R.string.button_christmas))
                }
            }
        }
    }
}