package com.example.justcreepinapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.justcreepinapp.data.Location

@Composable
//fun ListScreen(viewModel: AppViewModel, locations: List<Location>, onLocationClick: (Location) -> Unit){
//fun ListScreen(viewModel: AppViewModel, onAddLocationClick: () -> Unit, onLocationClick: (Location) -> Unit){
fun ListScreen(viewModel: AppViewModel, onBackClick: () -> Unit, onAddLocationClick: () -> Unit, onLocationClick: (Location) -> Unit){
    val locations = viewModel.locations.value
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    SnackbarHost(hostState = snackbarHostState)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(onClick = onBackClick) {
            Text(stringResource(R.string.button_back))
        }
        Spacer(Modifier.height(8.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            onClick = onAddLocationClick
        ) {
            Text(stringResource(R.string.button_add_location))
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(locations) { location ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(location.holiday, fontWeight = FontWeight.Bold)
                            Text(location.type, fontWeight = FontWeight.SemiBold)
                            Text("${location.latitude} | ${location.longitude}")
                        }
                        IconButton(onClick = {
                            // You can open a dialog or navigate to an edit screen here
                            //viewModel.editLocation(location)
                            onLocationClick(location)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Location",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        IconButton(onClick = {

                            /* onDelete(note.id) */
                            //viewModel.deleteLocation.value = location
                            //viewModel.showDialog.value = true

                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.button_delete_location),
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }

}