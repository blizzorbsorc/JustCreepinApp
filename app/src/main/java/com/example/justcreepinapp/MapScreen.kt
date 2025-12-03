package com.example.justcreepinapp

import android.location.Geocoder
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MapScreen(modifier: Modifier = Modifier){
    val viewModel: MapViewModel = viewModel()
    val location by viewModel.eventLocation.collectAsState()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 12f)
    }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState
                ) {
                    Marker(
                        state = MarkerState(position = location),
                        title = "Selected Location"
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = viewModel.cityName.value,
                    onValueChange = { viewModel.cityName.value = it },
                    label = { Text("Enter City") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

/* Button(
                    onClick = {
                        val geocoder = Geocoder(context)
                        val city = viewModel.cityName.value
                        val addresses = geocoder.getFromLocationName(city, 1)

                        if (!addresses.isNullOrEmpty()) {
                            val addr = addresses[0]
                            val newLatLng = LatLng(addr.latitude, addr.longitude)
                            viewModel.updateLocation(newLatLng)

                            coroutineScope.launch {
                                cameraPositionState.animate(
                                    CameraUpdateFactory.newLatLngZoom(newLatLng, 12f),
                                    1000
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Go")
                } */


                Button(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                val geocoder = Geocoder(context)
                                val city = viewModel.cityName.value

                                // Run geocoding off the main thread
                                val addresses = withContext(Dispatchers.IO) {
                                    geocoder.getFromLocationName(city, 1)
                                }

                                if (!addresses.isNullOrEmpty()) {
                                    val addr = addresses[0]
                                    val newLatLng = LatLng(addr.latitude, addr.longitude)
                                    viewModel.updateLocation(newLatLng)

                                    // animate camera on the main thread (still inside coroutine)
                                    cameraPositionState.animate(
                                        CameraUpdateFactory.newLatLngZoom(newLatLng, 12f),
                                        1000
                                    )
                                } else {
                                    // optional: show snackbar / toast for "not found"
                                }
                            } catch (e: Exception) {
                                // handle IOException or other errors from Geocoder
                                e.printStackTrace()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Go")
                }



            }

        }
    }
}
