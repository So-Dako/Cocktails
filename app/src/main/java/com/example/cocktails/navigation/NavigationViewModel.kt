package com.example.cocktails.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class NavigationViewModel(): ViewModel() {
    val backStack = mutableStateListOf<Screens>(Screens.Home)
}