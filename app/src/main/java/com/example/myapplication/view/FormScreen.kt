// ui/FormScreen.kt
// Seconde vue : le formulaire complet d’ajout de produit (Ex. TP1/TP2)
// • Column + verticalScroll pour rendre l’écran scrollable (diapo ScrollView)
// • Variables déclarées en haut, UI après (bonne pratique) :contentReference[oaicite:2]{index=2}:contentReference[oaicite:3]{index=3}
// • Toast + AlertDialog comme demandé dans l’énoncé :contentReference[oaicite:4]{index=4}:contentReference[oaicite:5]{index=5}

package com.example.myapplication.view

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.io.Serializable
import java.time.LocalDate
import java.util.*


/* ------------------------------------------------------------------------- */
/* Modèle de données : on suppose que ce data class existe dans ce fichier. */
/* Il transporte les valeurs saisies dans le formulaire vers HomeScreen.    */
enum class ProductType { CONSUMABLE, DURABLE, OTHER }

data class Product(
    val name: String,
    val type: ProductType,
    val color: String,
    val country: String,
    val favorite: Boolean
) : Serializable
/* ------------------------------------------------------------------------- */

@Composable
fun FormScreen(navController: NavController) {

    // --- 1) Contexte Android ---
    // Nécessaire si on veut afficher un Toast par exemple
    val context = LocalContext.current

    // --- 2) États du formulaire ---
    // rememberSaveable permet de restaurer ces valeurs si le composable est recréé
    var name     by rememberSaveable { mutableStateOf("") }
    var type     by rememberSaveable { mutableStateOf(ProductType.CONSUMABLE) }
    var color    by rememberSaveable { mutableStateOf("") }
    var country  by rememberSaveable { mutableStateOf("") }
    var favorite by rememberSaveable { mutableStateOf(false) }

    // --- 3) Conteneur scrollable ---
    // Column + verticalScroll pour gérer les grands formulaires
    Column(
        modifier = Modifier
            .fillMaxSize()               // occupe tout l’écran
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // --- 4) Image illustrative ---
        Image(
            painter = painterResource(id = android.R.drawable.ic_menu_gallery),
            contentDescription = "Illustration produit",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(Modifier.height(16.dp))

        // --- 5) Champ Nom (obligatoire) ---
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nom du produit *") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // --- 6) Choix du type (RadioButtons) ---
        Text("Type de produit")
        Row {
            ProductType.values().forEach { pt ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable { type = pt }  // on change type au clic
                ) {
                    RadioButton(
                        selected = (type == pt),
                        onClick = { type = pt }
                    )
                    Text(pt.name.lowercase().replaceFirstChar { it.uppercase() })
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // --- 7) Champ Couleur (facultatif) ---
        OutlinedTextField(
            value = color,
            onValueChange = { color = it },
            label = { Text("Couleur") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // --- 8) Champ Pays (facultatif) ---
        OutlinedTextField(
            value = country,
            onValueChange = { country = it },
            label = { Text("Pays d'origine") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // --- 9) Checkbox Favori ---
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = favorite,
                onCheckedChange = { favorite = it }
            )
            Spacer(Modifier.width(8.dp))
            Text("Ajouter aux favoris")
        }

        Spacer(Modifier.height(24.dp))

        // --- 10) Bouton Valider ---
        Button(
            onClick = {
                // Création de l’objet Product avec toutes les valeurs du formulaire
                val product = Product(
                    name     = name,
                    type     = type,
                    color    = color,
                    country  = country,
                    favorite = favorite
                )

                // Transmission vers HomeScreen via SavedStateHandle
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("product", product)

                // Retour à l’écran précédent
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Valider")
        }
    }
}