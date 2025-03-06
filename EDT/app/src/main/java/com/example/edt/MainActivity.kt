package com.example.edt

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.edt.ui.theme.EDTTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        enableEdgeToEdge()
        setContent {
            EDTTheme {
                App()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(){
    var titre by remember { mutableStateOf("Accueil") }
    var screen by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(titre) }
            )
        },
        bottomBar = {
            NavigationBar{
                NavigationBarItem(
                    icon = {Icon(Icons.Rounded.Home, contentDescription = null)},
                    label = {Text("Accueil")},
                    onClick = {
                        titre = "Accueil"
                        screen = 0
                    },
                    selected = screen == 0
                )
                NavigationBarItem(
                    icon = {Icon(Icons.Rounded.DateRange, contentDescription = null)},
                    label = {Text("Planning")},
                    onClick = {
                        titre = "Planning"
                        screen = 1
                    },
                    selected = screen == 1
                )
                NavigationBarItem(
                    icon = {Icon(Icons.Rounded.AccountBox, contentDescription = null)},
                    label = {Text("Compte")},
                    onClick = {
                        titre = "Compte"
                        screen = 2
                    },
                    selected = screen == 2
                )
            }
        },
    ){ innerPadding ->
        when (screen){
            0 -> { Accueil(innerPadding) }
            1 -> { Planning(innerPadding) }
            2 -> { Compte(innerPadding) }
        }
    }
}