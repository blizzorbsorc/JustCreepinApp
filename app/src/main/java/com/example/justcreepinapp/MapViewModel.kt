/*
package com.example.justcreepinapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
//import androidx.lifecycle.ViewModel
//import com.google.android.gms.maps.model.LatLng

class MapViewModel : ViewModel() {
    //private val _eventLocation = MutableStateFlow(LatLng(43.6532, -79.3832)) // Default: Toronto

    private val _eventLocation = MutableStateFlow(LatLng(43.46930922156322, -80.53361602943244)) // Default: Waterloo Park
    //43.46930922156322, -80.53361602943244
    val eventLocation: StateFlow<LatLng> = _eventLocation
    var cityName = mutableStateOf("Waterloo")
    fun updateLocation(newLocation: LatLng) {
        _eventLocation.value = newLocation
    }
}*/
