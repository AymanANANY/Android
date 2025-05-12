// ui/HomeScreen.kt
// Première vue : un simple écran avec un bouton qui ouvre le formulaire (Ex. TP3)
//
// Bonne pratique : séparer la logique/navigation et l’UI, déclarer les variables
// avant les composables (cf. diapo « Dans une fonction composable, il est mieux
// de ne pas mélanger les variables et les composants » :contentReference[oaicite:0]{index=0}:contentReference[oaicite:1]{index=1})

package com.example.myapplication.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.Routes

@Composable
fun HomeScreen(navController: NavController) {

    // UI ---------------------------------------------------------------------
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate(Routes.Form) }) {
            Text("Ajouter un produit")
        }

    }
}
