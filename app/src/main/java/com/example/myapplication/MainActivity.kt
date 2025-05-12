package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.Routes
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.view.FormScreen
import com.example.myapplication.view.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                /* ---------- Graphe ---------- */
                NavHost(
                    navController = navController,
                    startDestination = Routes.Home
                ) {
                    composable<Routes.Home> { HomeScreen(navController) }
                    composable<Routes.Form> { FormScreen(navController) }
                }

            }
            }
        }
    }
