package com.example.cocktails.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.cocktails.R
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun IngredientCard(
    ingredient: String,
    measure: String,
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    )
    {
        GlideImage(
            imageModel = { "https://www.thecocktaildb.com/images/ingredients/$ingredient.png" },
            modifier = Modifier
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
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        )
        Text(
            text = "$measure $ingredient",
            textAlign = TextAlign.Center
        )
    }
}