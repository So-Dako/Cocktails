package com.example.cocktails.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.cocktails.R
import com.example.cocktails.network.AlcoholicCocktail
import com.example.cocktails.network.NonAlcoholicCocktail
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CocktailCard(alcoholicCocktail: AlcoholicCocktail, modifier: Modifier = Modifier) {
    Card(colors = CardDefaults.cardColors(
        containerColor = Color.Transparent
    ),
        shape = RectangleShape
        ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            GlideImage(
                imageModel = {alcoholicCocktail.strDrinkThumb},
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                requestOptions = {
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_connection_error)
                }
            )
            Text(
                text = alcoholicCocktail.strDrink,
                textAlign = TextAlign.Center,
                modifier = modifier)
        }
    }
}

@Composable
fun CocktailCard(nonAlcoholicCocktail: NonAlcoholicCocktail, modifier: Modifier = Modifier) {
    Card(colors = CardDefaults.cardColors(
        containerColor = Color.Transparent
    ),
        shape = RectangleShape
    ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            GlideImage(
                imageModel = {nonAlcoholicCocktail.strDrinkThumb},
                modifier = modifier
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
                text = nonAlcoholicCocktail.strDrink,
                textAlign = TextAlign.Center,
                modifier = modifier)
        }
    }
}