package com.example.edt

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun Compte(pad : PaddingValues,user: Compte?,onConnexionSuccess: (Compte) -> Unit,onSeDeconnecter: () -> Unit) {
    var showMenuCompte by remember { mutableStateOf(true) }
    var showInscription by remember { mutableStateOf(false) }
    var showConnexion by remember { mutableStateOf(false) }
    var logInscrit by remember { mutableStateOf<String?>(null) }
    var logConnecte by remember { mutableStateOf<Compte?>(null) }

    when{
        user != null -> {
            Profil(pad,user,onSeDeconnecter)
        }
        logInscrit != null -> {
            Recap(pad, logInscrit!!){
                showConnexion = true
                logInscrit = null
            }
        }
        showMenuCompte -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(pad).padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(fontSize = 12.sp, text = "Vous n'êtes pas encore inscrit ou connecté.")
                Text(fontSize = 12.sp, text = "Choisissez une option :")
                Row {
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
                            showConnexion = true
                            showMenuCompte = false
                        }
                    ) {
                        Text(text = "Connexion")
                    }
                }
            }
        }
        showInscription -> {
            Inscription(
                pad,
                onCompteAdded = { compte ->
                    logInscrit = compte.login
                    showInscription = false
                }
            )
        }
        showConnexion -> {
            Connexion(
                pad,
                onConnexionSuccess = { co ->
                    logConnecte = co
                    onConnexionSuccess(logConnecte!!)
                }
            )
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
    var mailValide by remember { mutableStateOf(true) }

    var isOption1Checked by remember { mutableStateOf(false) }
    var isOption2Checked by remember { mutableStateOf(false) }
    var isOption3Checked by remember { mutableStateOf(false) }

    var loginError by remember { mutableStateOf<String?>(null) }
    var mdpError by remember { mutableStateOf<String?>(null) }

    val coroutine = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(pad).padding(8.dp).verticalScroll(scrollState)
    ) {
        OutlinedTextField(
            label = {
                Text("Login :")
            },
            value = login,
            onValueChange = {
                login = it
                loginError = when {
                    !it.matches(Regex("^[A-Za-z][A-Za-z0-9]{0,9}$")) ->
                        "Doit commencer par une lettre et max 10 caractères"
                    else -> null
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = loginError != null
        )
        if (loginError != null) {
            Text(text = loginError!!, color = Color.Red, fontSize = 12.sp)
        }
        OutlinedTextField(
            label = {
                Text("Mot de passe :")
            },
            value = mdp,
            onValueChange = {
                mdp = it
                mdpError = when {
                    it.length != 6 -> "Doit contenir exactement 6 caractères"
                    else -> null
                }
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            isError = mdpError != null
        )
        if (mdpError != null) {
            Text(text = mdpError!!, color = Color.Red, fontSize = 12.sp)
        }
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
                if(it.length <= 8 && it.all { char -> char.isDigit() }){
                    dateNaissance = it
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            label = {
                Text("Téléphone :")
            },
            value = telephone,
            onValueChange = {
                if(it.length <= 10 && it.all { char -> char.isDigit() }) {
                    telephone = it
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            label = {
                Text("Mail :")
            },
            value = mail,
            onValueChange = {
                mail = it
                mailValide = android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = !mailValide,
            modifier = Modifier.fillMaxWidth()
        )
        if (!mailValide && mail.isNotEmpty()) {
            Text(text = "Adresse mail invalide",color = Color.Red,fontSize = 12.sp)
        }
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
                    val verif_login = compteDao.getCompteByLogin(login)
                    if (verif_login != null){
                        loginError = "Login déjà utilisé"
                    }else{
                        loginError = null
                        val compte = Compte(
                            login = login,
                            mdp = mdp,
                            nom = nom,
                            prenom = prenom,
                            dateNaissance = FormatDate(dateNaissance),
                            telephone = telephone,
                            mail = mail,
                            interets = listOfNotNull(
                                if (isOption1Checked) "Sport" else null,
                                if (isOption2Checked) "Musique" else null,
                                if (isOption3Checked) "Lecture" else null
                            ).joinToString(", "),
                            evenement1 = "vide", evenement2 = "vide", evenement3 = "vide", evenement4 = "vide"
                        )
                        compteDao.insert(compte)
                        onCompteAdded(compte)
                    }
                }
            },
            enabled = loginError == null && mdpError == null
        ) {
            Text(text="S'inscrire")
        }
    }
}

@Composable
fun Connexion(pad : PaddingValues, onConnexionSuccess: (Compte) -> Unit){
    val ctx = LocalContext.current
    val database = AppDatabase.getDatabase(ctx)
    val compteDao = database.compteDao()

    var login by remember { mutableStateOf("") }
    var mdp by remember { mutableStateOf("") }
    var connexionError by remember { mutableStateOf<String?>(null) }

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
            modifier = Modifier.fillMaxWidth(),
            isError = connexionError != null
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
            modifier = Modifier.fillMaxWidth(),
            isError = connexionError != null
        )
        if (connexionError != null) {
            Text(text = connexionError!!, color = Color.Red, fontSize = 12.sp)
        }
        Button(
            onClick = {
                coroutine.launch {
                    val existingCompte = compteDao.getCompteByLogin(login)
                    if (existingCompte == null || existingCompte.mdp != mdp) {
                        connexionError = "Login ou mot de passe incorrect"
                    } else {
                        withContext(Dispatchers.Main){
                            connexionError = null
                            onConnexionSuccess(existingCompte)
                        }
                    }
                }
            }
        ) {
            Text(text="Connexion")
        }
    }
}

@Composable
fun Recap(pad: PaddingValues, login: String, onSeConnecter: () -> Unit) {
    val ctx = LocalContext.current
    val database = AppDatabase.getDatabase(ctx)
    val compteDao = database.compteDao()

    var compte by remember { mutableStateOf<Compte?>(null) }
    val coroutine = rememberCoroutineScope()

    LaunchedEffect(login) {
        coroutine.launch {
            compte = compteDao.getCompteByLogin(login)
        }
    }

    Column(modifier = Modifier.padding(pad).padding(16.dp)) {
        if (compte != null) {
            Text(text = "Récapitulatif :", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Login: ${compte!!.login}")
            Text(text = "Mot de passe: *******")
            Text(text = "Nom: ${compte!!.nom}")
            Text(text = "Prénom: ${compte!!.prenom}")
            Text(text = "Date de naissance: ${compte!!.dateNaissance}")
            Text(text = "Téléphone: ${compte!!.telephone}")
            Text(text = "Mail: ${compte!!.mail}")
            Text(text = "Intérêts: ${compte!!.interets}")
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { onSeConnecter() }
            ) {
                Text(text = "Se connecter")
            }
        } else {
            Text(text = "Chargement des informations...", fontSize = 14.sp)
        }
    }
}

@Composable
fun Profil(pad: PaddingValues,user: Compte, onSeDeconnecter: () -> Unit){
    val ctx = LocalContext.current
    val database = AppDatabase.getDatabase(ctx)
    val compteDao = database.compteDao()

    var compte by remember { mutableStateOf<Compte?>(null) }
    val coroutine = rememberCoroutineScope()

    LaunchedEffect(user.login) {
        coroutine.launch {
            compte = compteDao.getCompteByLogin(user.login)
        }
    }

    Column(
        modifier = Modifier.padding(pad).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (compte != null) {
            Text(
                text = compte!!.login,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = R.drawable.user),
                contentDescription = "Photo de profil",
                modifier = Modifier.size(80.dp).clip(CircleShape).border(2.dp,Color.Gray,CircleShape)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TabInfo("Login", compte!!.login)
                TabInfo("Mot de passe", "********")
                TabInfo("Nom", compte!!.nom)
                TabInfo("Prénom", compte!!.prenom)
                TabInfo("Date de naissance", compte!!.dateNaissance)
                TabInfo("Téléphone", compte!!.telephone)
                TabInfo("Mail", compte!!.mail)
                TabInfo("Intérêts", compte!!.interets)
            }
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { onSeDeconnecter() }
            ) {
                Text(text = "Se deconnecter")
            }
        } else {
            Text(text = "Chargement des informations...", fontSize = 14.sp)
        }
    }
}

fun FormatDate(input: String): String{
    val caracteres = input.filter { it.isDigit() }
    return when (caracteres.length) {
        8 -> "${caracteres.take(2)}/${caracteres.substring(2,4)}/${caracteres.drop(4)}"
        else -> caracteres
    }
}

@Composable
fun TabInfo(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Bold)
        Text(text = value)
    }
}