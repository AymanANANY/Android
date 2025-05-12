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
import java.time.LocalDate
import java.util.*

// ---------------------------------------------------------------------------
// Types/model

/** Type de produit : consommable, durable ou autre. */
enum class ProductType { CONSUMABLE, DURABLE, OTHER }

// ---------------------------------------------------------------------------
// Composable écran

@Composable
fun FormScreen(navController: NavController) {

    // Contexte Android (pour Toast + DatePicker)
    val context = LocalContext.current

    // --------------------------- ÉTAT (rememberSaveable) --------------------
    var name      by rememberSaveable { mutableStateOf("") }
    var type      by rememberSaveable { mutableStateOf(ProductType.CONSUMABLE) }
    var date      by rememberSaveable { mutableStateOf(LocalDate.now()) }
    var color     by rememberSaveable { mutableStateOf("") }
    var country   by rememberSaveable { mutableStateOf("") }
    var favorite  by rememberSaveable { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    // --------------------------- DatePickerDialog ---------------------------
    val calendar = Calendar.getInstance()
    val datePicker = DatePickerDialog(
        context,
        { _, y, m, d -> date = LocalDate.of(y, m + 1, d) },
        date.year, date.monthValue - 1, date.dayOfMonth
    )

    // --------------------------- UI ----------------------------------------
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())      // scrollable (diapo 8) :contentReference[oaicite:6]{index=6}:contentReference[oaicite:7]{index=7}
    ) {
        // Image placeholder (diapo Image) :contentReference[oaicite:8]{index=8}:contentReference[oaicite:9]{index=9}
        Image(
            painter = painterResource(id = android.R.drawable.ic_menu_gallery),
            contentDescription = "Illustration",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(Modifier.height(16.dp))

        // Champ texte Nom (obligatoire)
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nom du produit *") },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // Type de produit (RadioButtons)
        Text("Type de produit", style = MaterialTheme.typography.bodyMedium)
        Row {
            ProductType.values().forEach { pt ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable { type = pt }
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

        // Sélecteur de date
        OutlinedButton(onClick = { datePicker.show() }) {
            Text("Date d'achat : $date")
        }

        Spacer(Modifier.height(16.dp))

        // Couleur (facultatif)
        OutlinedTextField(
            value = color,
            onValueChange = { color = it },
            label = { Text("Couleur") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // Pays d’origine (facultatif)
        OutlinedTextField(
            value = country,
            onValueChange = { country = it },
            label = { Text("Pays d'origine") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // Favori (Checkbox)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(favorite, onCheckedChange = { favorite = it })
            Spacer(Modifier.width(8.dp))
            Text("Ajouter aux favoris")
        }

        Spacer(Modifier.height(24.dp))

        // ------------------- Bouton Valider --------------------------------
        Button(
            onClick = {
                if (name.isBlank()) {
                    Toast.makeText(context, "Le nom est requis", Toast.LENGTH_SHORT).show()
                } else {
                    showDialog = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Valider")
        }

        // ------------------- AlertDialog récapitulatif ---------------------
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Détails du produit") },
                text = {
                    Text(
                        buildString {
                            append("Nom : $name\n")
                            append("Type : ${type.name}\n")
                            append("Date : $date\n")
                            append("Couleur : ${color.ifBlank { "—" }}\n")
                            append("Pays : ${country.ifBlank { "—" }}\n")
                            append("Favori : ${if (favorite) "Oui" else "Non"}")
                        }
                    )
                },
                confirmButton = {
                    Button(onClick = { showDialog = false }) { Text("OK") }
                }
            )
        }
    }
}
