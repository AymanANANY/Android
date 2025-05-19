// ui/HomeScreen.kt
// Première vue : un simple écran avec un bouton qui ouvre le formulaire (Ex. TP3)
//
// Bonne pratique : séparer la logique/navigation et l’UI, déclarer les variables
// avant les composables (cf. diapo « Dans une fonction composable, il est mieux
// de ne pas mélanger les variables et les composants » :contentReference[oaicite:0]{index=0}:contentReference[oaicite:1]{index=1})

package com.example.myapplication.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.data.Routes
import androidx.compose.material3.MaterialTheme


@Composable
fun HomeScreen(navController: NavController) {
    // 1) On déclare la liste des produits sous forme d’état « saveable »
    //    → rememberSaveable : la valeur est rétablie après un retour de navigation
    //    products est une List<Product>, initialement vide
    var products by rememberSaveable { mutableStateOf(listOf<Product>()) }

    // 2) On écoute la clé "product" du SavedStateHandle pour récupérer
    //    chaque nouvel objet Product renvoyé par FormScreen
    //    getStateFlow("product", null) renvoie un Flow<Product?> qui émet
    //    la dernière valeur posée (ou null par défaut)
    val newProduct by navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow("product", null)
        ?.collectAsState()                // on transforme le flow en State<Product?>
        ?: remember { mutableStateOf<Product?>(null) }

    // 3) À chaque fois que newProduct change (i.e. on revient du formulaire),
    //    on ajoute le produit à la liste, puis on vide la clé pour ne pas le
    //    relire en boucle.
    LaunchedEffect(newProduct) {
        newProduct?.let { prod ->
            products = products + prod    // ajoute à la fin de la liste
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.remove<Product>("product")  // on efface pour éviter les doublons
        }
    }

    // ----- Section UI -----
    Column(
        modifier = Modifier
            .fillMaxSize()               // prend toute la surface de l’écran
            .padding(16.dp),             // marges autour
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 4) Liste déroulante (LazyColumn) pour afficher tous les produits
        LazyColumn(
            modifier = Modifier
                .weight(1f)              // prend tout l’espace vertical restant
                .fillMaxWidth(),         // largeur maximale
            verticalArrangement = Arrangement.spacedBy(8.dp) // espace entre les items
        ) {
            items(products) { prod ->
                ProductRow(prod)         // composable qui affiche un produit
            }
        }

        Spacer(Modifier.height(16.dp))

        // 5) Bouton pour naviguer vers l’écran de formulaire
        Button(
            onClick = { navController.navigate(Routes.Form) },
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(20.dp)
        ) {
            Text("Ajouter un produit", fontSize = 25.sp)
        }
    }
}

/**
 * Petit « row » qui affiche :
 * - le nom du produit (en grand)
 * - le pays s’il est renseigné (en petit)
 * - une étoile si c’est un favori
 */
@Composable
fun ProductRow(prod: Product) {
    Row(
        modifier = Modifier
            .fillMaxWidth()             // ligne pleine largeur
            .padding(8.dp),             // petit padding autour
        horizontalArrangement = Arrangement.SpaceBetween // nom à gauche, étoile à droite
    ) {
        Column {
            // Nom en bodyLarge
            Text(text = prod.name, style = MaterialTheme.typography.bodyLarge)
            // Pays en bodySmall si non vide
            if (prod.country.isNotBlank()) {
                Text(text = prod.country, style = MaterialTheme.typography.bodySmall)
            }
            if (prod.color.isNotBlank()) {
                Text(text = prod.country, style = MaterialTheme.typography.bodySmall)
            }
        }
        // Étoile si favori
        if (prod.favorite) {
            Text("★", fontSize = 20.sp)
        }
    }
}
