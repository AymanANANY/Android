package com.example.myapplication.data

import retrofit2.http.GET

interface ApiService {
    @GET("all")
    suspend fun getCountries(): List<Country>
}


