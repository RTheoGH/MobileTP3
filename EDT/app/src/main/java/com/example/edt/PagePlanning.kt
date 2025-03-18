package com.example.edt

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun Planning(pad : PaddingValues,user : Compte?){
    Column(
        modifier = Modifier.padding(pad).padding(16.dp)
    ) {
        if(user != null){
            Text(text = "Bienvenue sur votre planning, ${user.login} !")
            Spacer(modifier = Modifier.height(16.dp))
            Co_Planning(user)

        }else{
            Text(text = "Veuillez vous connecter pour accéder à votre planning.")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Co_Planning(user : Compte){
    val ctx = LocalContext.current
    val database = AppDatabase.getDatabase(ctx)
    val compteDao = database.compteDao()

    val coroutine = rememberCoroutineScope()

    var e1 by remember { mutableStateOf("") }
    var e2 by remember { mutableStateOf("") }
    var e3 by remember { mutableStateOf("") }
    var e4 by remember { mutableStateOf("") }

    LaunchedEffect(user) {
        coroutine.launch {
            e1 = compteDao.getEvenement1(user.login)
            e2 = compteDao.getEvenement2(user.login)
            e3 = compteDao.getEvenement3(user.login)
            e4 = compteDao.getEvenement4(user.login)
        }
    }

    Text(text = " 8h", fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
    OutlinedTextField(
        value = e1,
        onValueChange = {
            e1 = it
            coroutine.launch {
                compteDao.updateEvenement1(user.login, e1)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(190,228,252,255),
            unfocusedContainerColor = Color(190,228,252,255),
            unfocusedIndicatorColor = Color.Transparent
        )
    )
    Text(text = " 10h", fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
    TextField(
        value = e2,
        onValueChange = {
            e2 = it
            coroutine.launch {
                compteDao.updateEvenement2(user.login, e2)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(190,228,252,255),
            unfocusedContainerColor = Color(190,228,252,255),
            unfocusedIndicatorColor = Color.Transparent
        )
    )
    Text(text = " 12h", fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = " 14h", fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
    TextField(
        value = e3,
        onValueChange = {
            e3 = it
            coroutine.launch {
                compteDao.updateEvenement3(user.login, e3)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(190,228,252,255),
            unfocusedContainerColor = Color(190,228,252,255),
            unfocusedIndicatorColor = Color.Transparent
        )
    )
    Text(text = " 16h", fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
    TextField(
        value = e4,
        onValueChange = {
            e4 = it
            coroutine.launch {
                compteDao.updateEvenement4(user.login,e4)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(190,228,252,255),
            unfocusedContainerColor = Color(190,228,252,255),
            unfocusedIndicatorColor = Color.Transparent
        )
    )
    Text(text = " 18h", fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
}