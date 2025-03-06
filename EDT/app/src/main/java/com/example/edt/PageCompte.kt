package com.example.edt

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun Compte(pad : PaddingValues){

    val ctx = LocalContext.current
    val database = AppDatabase.getDatabase(ctx)
    val compteDao = database.compteDao()

    var login by remember { mutableStateOf("") }
    var mdp by remember { mutableStateOf("") }
    var nom by remember { mutableStateOf("") }
    var prenom by remember { mutableStateOf("") }
    var telephone by remember { mutableStateOf("") }
    var mail by remember { mutableStateOf("") }

    var isOption1Checked by remember { mutableStateOf(false) }
    var isOption2Checked by remember { mutableStateOf(false) }
    var isOption3Checked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(pad).padding(8.dp)
    ) {
        OutlinedTextField(
            label = {
                Text("Login :")
            },
            value = login,
            onValueChange = {
                login = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            label = {
                Text("Mot de passe :")
            },
            value = mdp,
            onValueChange = {
                mdp = it
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            label = {
                Text("Nom :")
            },
            value = nom,
            onValueChange = {
                nom = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            label = {
                Text("Prénom :")
            },
            value = prenom,
            onValueChange = {
                prenom = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            label = {
                Text("Téléphone :")
            },
            value = telephone,
            onValueChange = {
                telephone = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            label = {
                Text("Mail :")
            },
            value = mail,
            onValueChange = {
                mail = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Row(verticalAlignment = Alignment.CenterVertically){
            Checkbox(
                checked = isOption1Checked,
                onCheckedChange = { isOption1Checked = it }
            )
            Text("Sport")
            Checkbox(
                checked = isOption2Checked,
                onCheckedChange = { isOption2Checked = it }
            )
            Text(text = "Musique")
            Checkbox(
                checked = isOption3Checked,
                onCheckedChange = { isOption3Checked = it }
            )
            Text(text = "Lecture")
        }
        Button(
            onClick = {}
        ) {
            Text(text="Soumettre")
        }
    }

}