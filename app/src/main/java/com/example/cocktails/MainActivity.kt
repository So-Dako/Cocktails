package com.example.cocktails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocktails.navigation.AppNavigation
import com.example.cocktails.navigation.NavigationViewModel
import com.example.cocktails.ui.screens.Home
import com.example.cocktails.ui.theme.CocktailsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CocktailsTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navigationViewModel: NavigationViewModel = viewModel()
                    val backStack = navigationViewModel.backStack
                    AppNavigation(backStack = backStack)
                }
            }
        }
    }
}

