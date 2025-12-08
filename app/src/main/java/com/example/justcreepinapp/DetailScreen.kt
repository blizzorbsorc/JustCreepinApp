package com.example.justcreepinapp

import android.location.Address
import android.location.Geocoder
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun DetailScreen(
    viewModel: AppViewModel,
    locationId: Int? = null,
    onBackClick: () -> Unit
) {
    val editLocation = locationId != null
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var searchQuery by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<Address>>(emptyList()) }
    var isSearching by remember { mutableStateOf(false) }
    var showResults by remember { mutableStateOf(false) }

    LaunchedEffect(locationId) {
        if (editLocation) {
            locationId?.let { viewModel.loadLocationFieldValues(it) }
        } else {
            viewModel.resetFieldValues()
        }
    }

    // Gradient background matching home screen
    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF6650a4),
            Color(0xFFD0BCFF),
            Color(0xFFEFB8C8)
        ),
        start = Offset(0f, 0f),
        end = Offset(1000f, 1000f)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradient)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            item {
                // Top bar with back button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }

                    Text(
                        text = if (editLocation) stringResource(R.string.detail_edit_title) else stringResource(R.string.detail_add_title),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Spacer(Modifier.height(8.dp))

                // Holiday Display
                Text(
                    text = stringResource(R.string.label_holiday, viewModel.holiday.value),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Type Field
                OutlinedTextField(
                    value = viewModel.type.value,
                    onValueChange = { viewModel.type.value = it },
                    label = { Text(stringResource(R.string.label_decoration_type)) },
                    placeholder = { Text(stringResource(R.string.placeholder_decoration_type)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.95f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.95f),
                        focusedBorderColor = Color(0xFF6650a4),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        focusedLabelColor = Color(0xFF6650a4),
                        unfocusedLabelColor = Color(0xFF625b71)
                    )
                )

                Spacer(Modifier.height(16.dp))

                // Address Search Field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        showResults = it.isNotEmpty()
                    },
                    label = { Text(stringResource(R.string.label_search_address)) },
                    placeholder = { Text(stringResource(R.string.placeholder_address)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    trailingIcon = {
                        if (isSearching) {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(12.dp),
                                strokeWidth = 2.dp,
                                color = Color(0xFF6650a4)
                            )
                        } else {
                            IconButton(onClick = {
                                if (searchQuery.isNotEmpty()) {
                                    isSearching = true
                                    coroutineScope.launch {
                                        try {
                                            val geocoder = Geocoder(context)
                                            val results = withContext(Dispatchers.IO) {
                                                geocoder.getFromLocationName(searchQuery, 5)
                                            }
                                            searchResults = results ?: emptyList()
                                            showResults = searchResults.isNotEmpty()
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                            searchResults = emptyList()
                                        } finally {
                                            isSearching = false
                                        }
                                    }
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = stringResource(R.string.search),
                                    tint = Color(0xFF6650a4)
                                )
                            }
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.95f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.95f),
                        focusedBorderColor = Color(0xFF6650a4),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        focusedLabelColor = Color(0xFF6650a4),
                        unfocusedLabelColor = Color(0xFF625b71)
                    )
                )

                Spacer(Modifier.height(8.dp))

                // Search Results
                if (showResults && searchResults.isNotEmpty()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.95f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            searchResults.forEach { address ->
                                val addressLine = buildString {
                                    address.getAddressLine(0)?.let { append(it) }
                                }

                                Text(
                                    text = addressLine,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            viewModel.latitude.value = addressLine
                                            viewModel.longitude.value = "${address.latitude},${address.longitude}"
                                            searchQuery = addressLine
                                            showResults = false
                                        }
                                        .padding(12.dp),
                                    fontSize = 14.sp,
                                    color = Color(0xFF625b71)
                                )
                            }
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }

                // Selected Address Display
                if (viewModel.latitude.value.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF6650a4).copy(alpha = 0.2f)
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = stringResource(R.string.label_selected_address),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = viewModel.latitude.value,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }

                Spacer(Modifier.height(8.dp))

                // Save/Update Button with Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(8.dp)
                ) {
                    Button(
                        onClick = {
                            if (editLocation && locationId != null) {
                                viewModel.updateLocation(locationId) {
                                    onBackClick()
                                }
                            } else {
                                viewModel.addLocation {
                                    onBackClick()
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.95f)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
                        enabled = viewModel.latitude.value.isNotEmpty() && viewModel.type.value.isNotEmpty()
                    ) {
                        Text(
                            text = if (editLocation) stringResource(R.string.button_update_location) else stringResource(R.string.button_add_location_action),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6650a4)
                        )
                    }
                }

                // Delete Button (only when editing)
                if (editLocation && locationId != null) {
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = {
                            viewModel.deleteLocation(locationId) {
                                onBackClick()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFEF5350).copy(alpha = 0.9f)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.button_delete_location),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}