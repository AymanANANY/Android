package com.example.myapplication.data

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable data object Home : Routes
    @Serializable data object Form : Routes
}
