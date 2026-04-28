package com.example.cocktails.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.WineBar
import androidx.compose.material.icons.outlined.LocalDrink
import androidx.compose.material.icons.outlined.WineBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import com.example.cocktails.navigation.Screens
import com.example.cocktails.ui.screens.alcoholicCocktail.AlcoholicCocktailViewModel
import com.example.cocktails.ui.screens.alcoholicCocktail.AlcoholicCocktailsScreen
import com.example.cocktails.ui.screens.nonAlcoholocCocktail.NonAlcoholicCocktailViewModel
import com.example.cocktails.ui.screens.nonAlcoholocCocktail.NonAlcoholicCocktailsScreen

data class TabItem(
    val name: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    onNavigate: (Screens) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabItems = listOf(
        TabItem(
            name = "Alcoholic",
            unselectedIcon = Icons.Outlined.WineBar,
            selectedIcon = Icons.Filled.WineBar
        ),
        TabItem(
            name = "Non-alcoholic",
            unselectedIcon = Icons.Outlined.LocalDrink,
            selectedIcon = Icons.Filled.LocalDrink
        )
    )
    Scaffold(
        bottomBar = {
            TabRow(
                selectedTabIndex = selectedTab,
                modifier = modifier.navigationBarsPadding()
            ) {
                tabItems.forEachIndexed { index, item ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(text = item.name)
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedTab) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.name
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            if (selectedTab == 0) {
                val alcoholicCocktailViewModel: AlcoholicCocktailViewModel = viewModel()
                AlcoholicCocktailsScreen(
                    alcoholicCocktailUiState = alcoholicCocktailViewModel.alcoholicCocktailUiState,
                    onRetry = { alcoholicCocktailViewModel.getAlcoholicCocktails() },
                    onNavigate = onNavigate
                )
            } else {
                val nonAlcoholicCocktailViewModel: NonAlcoholicCocktailViewModel = viewModel()
                NonAlcoholicCocktailsScreen(
                    nonAlcoholicCocktailUiState = nonAlcoholicCocktailViewModel.nonAlcoholicCocktailUiState,
                    onRetry = { nonAlcoholicCocktailViewModel.getNonAlcoholicCocktails() },
                onNavigate = onNavigate)
            }
        }
    }
}
