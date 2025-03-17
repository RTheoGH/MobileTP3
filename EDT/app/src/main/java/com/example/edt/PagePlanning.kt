package com.example.edt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Planning(pad : PaddingValues,user : Compte?){
    Column(
        modifier = Modifier.padding(pad).padding(16.dp)
    ) {
        if(user != null){
            Text(text = "Bienvenue sur votre planning, ${user.login} !")
            Co_Planning(user)

        }else{
            Text(text = "Veuillez vous connecter pour accéder à votre planning.")
        }
    }
}

@Composable
fun Co_Planning(user : Compte){
    var planning by remember { mutableStateOf(user.planning) }

    Row(){

    }

}