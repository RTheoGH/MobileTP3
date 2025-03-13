package com.example.edt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Planning(pad : PaddingValues,nom : String){
    Column(
        modifier = Modifier.padding(pad).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if(nom != ""){
            Text(text = "Bienvenue sur votre planning, $nom !")
        }else{
            Text(text = "Veuillez vous connecter pour accéder à votre planning.")
        }
    }
}