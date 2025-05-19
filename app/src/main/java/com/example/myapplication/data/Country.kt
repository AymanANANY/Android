package com.example.myapplication.data

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name")
    val countryName: CountryName
)

data class CountryName(
    val common: String,
    val official: String,
    val nativeName: NativeName
)

data class NativeName(
    @SerializedName("eng")
    val english: Language
)

data class Language(
    val official: String
)
