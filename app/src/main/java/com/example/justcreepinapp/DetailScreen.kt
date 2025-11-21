package com.example.justcreepinapp

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.justcreepinapp.data.Location

@Composable
//fun DetailScreen(location: Location, locationId: Int? = null, onBackClick: () -> Unit) {
fun DetailScreen(viewModel: AppViewModel, locationId: Int? = null, onBackClick: () -> Unit) {

    val editLocation = locationId != null

    LaunchedEffect(locationId) {
        if(editLocation){
            locationId?.let{ viewModel.loadLocationFieldValues(it) }
        } else {
            viewModel.resetFieldValues()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(modifier = Modifier.padding(8.dp)) {
            item {
                Button(onClick = onBackClick) {
                    Text(stringResource(R.string.button_back))
                }
                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Holiday: ${viewModel.holiday.value }",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = viewModel.type.value,
                    onValueChange = {viewModel.type.value=it},
                    label = {Text(stringResource(R.string.text_type))},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = viewModel.latitude.value,
                    onValueChange = {viewModel.latitude.value=it},
                    label = {Text(stringResource(R.string.text_latitude))},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = viewModel.longitude.value,
                    onValueChange = {viewModel.longitude.value=it},
                    label = {Text(stringResource(R.string.text_longitude))},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {
                        if(editLocation && locationId != null) {
                            viewModel.updateLocation(locationId) {
                                onBackClick()
                            }
                        } else {
                            viewModel.addLocation {
                                onBackClick()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    //Text(if(editLocation) R.string.button_update_location else R.string.button_save_location)
                    Text(if(editLocation) "Update Location" else "Add Location")
                }

                if (editLocation && locationId != null) {
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = {
                            viewModel.updateLocation(locationId) {
                                onBackClick()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        //Text(if(editLocation) R.string.button_update_location else R.string.button_save_location)
                        Text("Delete Location")
                    }
                }
            }

        }
    }
}