package com.example.justcreepinapp

sealed class ValidationDetails {
    object Valid : ValidationDetails()
    //data class Invalid(val message: String) : ValidationDetails()
    data class Invalid(
        val typeInvalid: String? = null,
        val latitudeInvalid: String? = null,
        val longitudeInvalid: String? = null
    ) : ValidationDetails()
}


