package com.example.cocktails.ui.screens.ingredient

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.cocktails.R
import com.example.cocktails.navigation.Screens
import com.example.cocktails.network.Cocktail
import com.example.cocktails.ui.elements.CocktailCard
import com.example.cocktails.ui.screens.ErrorScreen
import com.example.cocktails.ui.screens.LoadingScreen
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun IngredientScreen(
    ingredientUiState: IngredientUiState,
    ingredient: String,
    onNavigate: (Screens) -> Unit,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    when(ingredientUiState) {
        is IngredientUiState.Loading -> LoadingScreen()
        is IngredientUiState.Success -> ResultScreen(
            cocktailList = ingredientUiState.cocktailList,
            ingredient = ingredient,
            onNavigate = onNavigate,
            onBack = onBack
            )
        is IngredientUiState.Error -> ErrorScreen(
            onRetry = onRetry
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen (
    cocktailList: List<Cocktail>,
    ingredient: String,
    onNavigate: (Screens) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = ingredient) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )

                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            GlideImage(
                imageModel = { "https://www.thecocktaildb.com/images/ingredients/$ingredient.png" },
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f),
                requestOptions = {
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                },
                failure = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_connection_error),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }
            )
            Text(
                text = ingredient,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .padding(8.dp))
            cocktailList.chunked(2).forEach { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    rowItems.forEach { item ->
                        CocktailCard(
                            item,
                            modifier = Modifier
                                .weight(1f)
                                .clickable(
                                    onClick = { onNavigate(Screens.CocktailDetail(item.idDrink)) }
                                )
                        )
                    }
                    repeat(2 - rowItems.size) {
                        Box(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}