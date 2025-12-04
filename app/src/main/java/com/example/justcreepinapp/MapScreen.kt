package com.example.justcreepinapp

import android.location.Geocoder
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
fun MapScreen(modifier: Modifier = Modifier, appViewModel: AppViewModel, onMarkerClick: (Int) -> Unit){
    val viewModel: MapViewModel = viewModel()
    val location by viewModel.eventLocation.collectAsState()

    // Get locations from viewmodel based on holiday selected
    val locations = appViewModel.locations.value

    // Get all types from locations
    val allTypes = remember(locations) {
        locations.map { it.type }.distinct().sorted()
    }

    // Multiselect filter
    var filterExpanded by remember { mutableStateOf(false) }
    var selectedTypes by remember { mutableStateOf<Set<String>>(emptySet()) }
    val allSelected = selectedTypes.isEmpty()

    // Filter locations based on selected types
    val filteredLocations = remember(locations, selectedTypes) {
        if (selectedTypes.isEmpty()) {
            locations
        } else {
            locations.filter { it.type in selectedTypes }
        }
    }

    // Starting camera position at first location
    val initialLatLng = locations.firstOrNull()?.let { loc ->
        val lat = loc.latitude.toDoubleOrNull()
        val lng = loc.longitude.toDoubleOrNull()
        if (lat != null && lng != null) LatLng(lat, lng) else location
    } ?: location

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialLatLng, 12f)
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
                    // Show marker for each location from database based on holiday selected
                    locations.forEach { loc ->
                        val lat = loc.latitude.toDoubleOrNull()
                        val lng = loc.longitude.toDoubleOrNull()
                        if (lat != null && lng != null) {
                            Marker(
                                state = MarkerState(position = LatLng(lat, lng)),
                                title = loc.holiday,
                                snippet = loc.type,
                                // makes marker clickable
                                onClick = {
                                    false
                                },

                                onInfoWindowClick = {
                                    onMarkerClick(loc.id)
                                }
                            )
                        }
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                TypeFilterDropdown(
                    allTypes = allTypes,
                    selectedTypes = selectedTypes,
                    onSelectionChange = { selectedTypes = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.cityName.value,
                    onValueChange = { viewModel.cityName.value = it },
                    label = { Text("Enter City") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeFilterDropdown(
    allTypes: List<String>,
    selectedTypes: Set<String>,
    onSelectionChange: (Set<String>) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    val showAll = selectedTypes.isEmpty()
    val displayText =
        if (showAll) "All types"
        else selectedTypes.joinToString(", ")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        // Anchor – NO label, read-only → avoids layered text
        TextField(
            value = displayText,
            onValueChange = { /* read-only */ },
            readOnly = true,
            modifier = Modifier
                .menuAnchor()      // 👈 VERY important, anchors the dropdown
                .fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Open type filter"
                )
            },
            singleLine = true
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // "All types" row
            DropdownMenuItem(
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = showAll,
                            onCheckedChange = null // handled by parent onClick
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("All types")
                    }
                },
                onClick = {
                    // Empty set means "no filter" → show everything
                    onSelectionChange(emptySet())
                }
            )

            // One row per type
            allTypes.forEach { type ->
                val isChecked = selectedTypes.contains(type)

                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = null
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(type)
                        }
                    },
                    onClick = {
                        val newSelection =
                            if (isChecked) selectedTypes - type
                            else selectedTypes + type
                        onSelectionChange(newSelection)
                    }
                )
            }
        }
    }
}