package com.example.justcreepinapp

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.justcreepinapp.data.Location
import com.example.justcreepinapp.data.LocationDatabaseHelper

class AppViewModel(application: Application) : AndroidViewModel(application) {
    val holidays = listOf(
        "Halloween",
        "Christmas"
    )
    private val dbHelper = LocationDatabaseHelper(application)
    var locations = mutableStateOf<List<Location>>(emptyList())
        private set
    var skin = mutableStateOf("")
    var holiday = mutableStateOf("")
    var type = mutableStateOf("")
    var latitude = mutableStateOf("")
    var longitude = mutableStateOf("")
    var detailValidationState = mutableStateOf<ValidationDetails>(ValidationDetails.Valid)
        private set

    // Validate details
    private fun validateDetails(): Boolean {
        var typeInvalid: String? = null
        var latitudeInvalid: String? = null
        var longitudeInvalid: String? = null

        // Validate type
        if (type.value.isBlank()) {
            typeInvalid = "Type cannot be empty"
        }

        // Validate latitude
        //val lat = latitude.value
        if (latitude.value.isBlank()) {
            latitudeInvalid = "Latitude cannot be empty"
        } else if (latitude.value.toDoubleOrNull() == null) {
            latitudeInvalid = "Invalid latitude format"
        }

        // Validate longitude
        //val lon = longitude.value
        if (longitude.value.isBlank()) {
            longitudeInvalid = "Longitude cannot be empty"
        } else if (longitude.value.toDoubleOrNull() == null) {
            longitudeInvalid = "Invalid longitude format"
        }

        // Update validation state
        detailValidationState.value =
            if (typeInvalid != null || latitudeInvalid != null || longitudeInvalid != null) {
                ValidationDetails.Invalid(
                    typeInvalid = typeInvalid,
                    latitudeInvalid = latitudeInvalid,
                    longitudeInvalid = longitudeInvalid)
            } else {
                ValidationDetails.Valid
            }

        return detailValidationState.value is ValidationDetails.Valid
    }

    init {
        loadLocations(holidays.first())
    }

    fun loadLocations(selectedHoliday: String) {
        holiday.value = selectedHoliday
        val allLocations = dbHelper.getAllLocations()
        locations.value = allLocations.filter { it.holiday == selectedHoliday }
    }

    fun resetFieldValues() {
        type.value = ""
        latitude.value = ""
        longitude.value = ""
    }

    fun loadLocationFieldValues(id: Int) {
        val spot = locations.value.firstOrNull { it.id == id }?: return
        holiday.value = spot.holiday
        type.value = spot.type
        latitude.value = spot.latitude
        longitude.value = spot.longitude
    }

    fun addLocation(onDone: () -> Unit = {}){
        //if (holiday.value.isBlank()) return

        if (!validateDetails()) return

        dbHelper.insertLocation(
            holiday = holiday.value,
            type = type.value,
            latitude = latitude.value,
            longitude = longitude.value
        )

        loadLocations(holiday.value)
        resetFieldValues()
        onDone()
    }

    fun updateLocation(
        id: Int,
        onDone: () -> Unit = {}
    ) {
        if (holiday.value.isBlank()) return

        dbHelper.updateLocation(
            id = id,
            holiday = holiday.value,
            type = type.value,
            latitude = latitude.value,
            longitude = longitude.value
        )

        loadLocations(holiday.value)
        onDone()
    }

    fun deleteLocation(
        id: Int,
        onDone: () -> Unit = {}
    ) {
        dbHelper.deleteLocation(id)
        if (holiday.value.isNotBlank()) {
            loadLocations(holiday.value)
        }
        onDone()
    }

}
