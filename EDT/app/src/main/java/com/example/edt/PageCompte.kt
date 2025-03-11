package com.example.edt

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun Compte(pad : PaddingValues) {
    var showMenuCompte by remember { mutableStateOf(true) }
    var showInscription by remember { mutableStateOf(false) }
    var showCompte by remember { mutableStateOf<Compte?>(null) }

    if (showMenuCompte) {
        Column(
            modifier = Modifier.fillMaxSize().padding(pad).padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                fontSize = 12.sp,
                text = "Vous n'êtes pas encore inscrit ou connecté."
            )
            Text(
                fontSize = 12.sp,
                text = "Choississez une option :"
            )
            Row{
                Button(
                    onClick = {
                        showInscription = true
                        showMenuCompte = false
                    }
                ) {
                    Text(text = "Inscription")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        showMenuCompte = false
                        // Connexion
                    }
                ) {
                    Text(text = "Connexion")
                }
            }

        }
    }

    if (showInscription){
        Inscription(
            pad,
            onCompteAdded = { compte ->
                showCompte = compte
                showInscription = false
            }
        )
    } else {
        if(showCompte != null){
            Profil(pad,compte = showCompte!!)
        }
    }
}

@Composable
fun Inscription(pad : PaddingValues,onCompteAdded: (Compte) -> Unit){
    val ctx = LocalContext.current
    val database = AppDatabase.getDatabase(ctx)
    val compteDao = database.compteDao()

    var login by remember { mutableStateOf("") }
    var mdp by remember { mutableStateOf("") }
    var nom by remember { mutableStateOf("") }
    var prenom by remember { mutableStateOf("") }
    var dateNaissance by remember { mutableStateOf("") }
    var telephone by remember { mutableStateOf("") }
    var mail by remember { mutableStateOf("") }

    var isOption1Checked by remember { mutableStateOf(false) }
    var isOption2Checked by remember { mutableStateOf(false) }
    var isOption3Checked by remember { mutableStateOf(false) }

    val coroutine = rememberCoroutineScope()

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
                Text("Date de naissance :")
            },
            value = dateNaissance,
            onValueChange = {
                dateNaissance = it
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
            onClick = {
                coroutine.launch {
                    val compte = Compte(
                        login = login,
                        mdp = mdp,
                        nom = nom,
                        prenom = prenom,
                        dateNaissance = dateNaissance,
                        telephone = telephone,
                        mail = mail,
                        interets = listOfNotNull(
                            if (isOption1Checked) "Sport" else null,
                            if (isOption2Checked) "Musique" else null,
                            if (isOption3Checked) "Lecture" else null
                        ).joinToString(", ")
                    )
                    compteDao.insert(compte)
                    onCompteAdded(compte)
                }
            }
        ) {
            Text(text="Soumettre")
        }
    }
}

@Composable
fun Connexion(pad : PaddingValues){
    var login by remember { mutableStateOf("") }
    var mdp by remember { mutableStateOf("") }

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
        Button(
            onClick = {
                // Connexion
            }
        ) {
            Text(text="Connexion")
        }
    }
}

@Composable
fun Profil(pad: PaddingValues,compte: Compte){
    // Améliorer pour faire une vrai page de profil
    Column(modifier = Modifier.padding(pad).padding(16.dp)) {
        Text(text = "Login: ${compte.login}")
        Text(text = "Mot de passe: ${compte.mdp}")
        Text(text = "Nom: ${compte.nom}")
        Text(text = "Prénom: ${compte.prenom}")
        Text(text = "Date de naissance: ${compte.dateNaissance}")
        Text(text = "Téléphone: ${compte.telephone}")
        Text(text = "Mail: ${compte.mail}")
        Text(text = "Intérêts: ${compte.interets}")
    }
}