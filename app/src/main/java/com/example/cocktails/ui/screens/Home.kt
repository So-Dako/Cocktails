package com.example.cocktails.ui.screens

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocktails.navigation.NavigationViewModel
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
    val navigationViewModel: NavigationViewModel = viewModel()
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
                selectedTabIndex = navigationViewModel.selectedTab,
                modifier = modifier.navigationBarsPadding()
            ) {
                tabItems.forEachIndexed { index, item ->
                    Tab(
                        selected = navigationViewModel.selectedTab == index,
                        onClick = { navigationViewModel.selectTab(index) },
                        text = {
                            Text(text = item.name)
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == navigationViewModel.selectedTab) {
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
        Box(modifier = modifier.padding(innerPadding)) {
            val navigationViewModel: NavigationViewModel = viewModel()
            if (navigationViewModel.selectedTab == 0) {
                val alcoholicCocktailViewModel: AlcoholicCocktailViewModel = viewModel()
                AlcoholicCocktailsScreen(
                    alcoholicCocktailUiState = alcoholicCocktailViewModel.alcoholicCocktailUiState,
                    onRetry = { alcoholicCocktailViewModel.getAlcoholicCocktails() },
                    onNavigate = onNavigate,
                    gridState = navigationViewModel.alcoholicCocktailGridState
                )
            } else {
                val nonAlcoholicCocktailViewModel: NonAlcoholicCocktailViewModel = viewModel()
                NonAlcoholicCocktailsScreen(
                    nonAlcoholicCocktailUiState = nonAlcoholicCocktailViewModel.nonAlcoholicCocktailUiState,
                    onRetry = { nonAlcoholicCocktailViewModel.getNonAlcoholicCocktails() },
                    onNavigate = onNavigate,
                    gridState = navigationViewModel.nonAlcoholicCocktailGridState
                )
            }
        }
    }
}
