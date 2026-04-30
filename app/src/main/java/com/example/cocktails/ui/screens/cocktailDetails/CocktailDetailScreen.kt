package com.example.cocktails.ui.screens.cocktailDetails

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.cocktails.R
import com.example.cocktails.navigation.Screens
import com.example.cocktails.network.CocktailDetails
import com.example.cocktails.ui.elements.IngredientCard
import com.example.cocktails.ui.screens.ErrorScreen
import com.example.cocktails.ui.screens.LoadingScreen
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CocktailDetailScreen(cocktailDetailUiState: CocktailDetailUiState,
                         cocktailId: String,
                         onNavigate: (Screens) -> Unit,
                         modifier: Modifier = Modifier){
    val cocktailDetailsViewModel: CocktailDetailsViewModel = viewModel()
    when(cocktailDetailUiState) {
        is CocktailDetailUiState.Loading -> LoadingScreen()
        is CocktailDetailUiState.Success -> ResultScreen(
            cocktailDetail = cocktailDetailUiState.cocktailDetails,
            onNavigate = onNavigate
            )
        is CocktailDetailUiState.Error -> ErrorScreen( onRetry = {
            cocktailDetailsViewModel.getDrinkDetails(cocktailId)
        })
    }
}

@Composable
fun ResultScreen(
    cocktailDetail: CocktailDetails,
    onNavigate: (Screens) -> Unit,
    modifier: Modifier = Modifier
){
    val pairs = cocktailDetail.getListOfMeasures()
        .zip(cocktailDetail.getListOfIngredients())

    Scaffold(){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                imageModel = {cocktailDetail.strDrinkThumb},
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
                text = cocktailDetail.strDrink,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .padding(8.dp))
            Text(
                text = "Ingredients",
                textAlign = TextAlign.Center,
                modifier = modifier.padding(8.dp)
            )
            pairs.chunked(3).forEach { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    rowItems.forEach { (measure, ingredient) ->
                            IngredientCard(
                                ingredient = ingredient,
                                measure = measure,
                                modifier = modifier
                                    .clickable(
                                    onClick = { onNavigate(Screens.IngredientInfo(ingredient)) }
                                )
                                    .weight(1f)
                            )
                    }
                    repeat(3 - rowItems.size) {
                        Box(modifier = Modifier.weight(1f))
                    }
                }
            }
            Text(
                text = "Instructions",
                textAlign = TextAlign.Center,
                modifier = modifier.padding(8.dp)
            )
            Text(
                text = cocktailDetail.strInstructions,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(8.dp)
            )
            Text(
                text = "Glass",
                textAlign = TextAlign.Center,
                modifier = modifier.padding(8.dp)
            )
            Text(
                text = cocktailDetail.strGlass,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(8.dp)
            )
        }
    }
}