package com.example.cocktails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocktails.ui.screens.AlcoholicCocktailUiState
import com.example.cocktails.ui.screens.CocktailViewModel
import com.example.cocktails.ui.theme.CocktailsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CocktailsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val cocktailViewModel: CocktailViewModel = viewModel()
                    HomeScreen(cocktailViewModel.alcoholicCocktailUiState, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun HomeScreen(alcoholicCocktailUiState: AlcoholicCocktailUiState, modifier: Modifier = Modifier) {
    when (alcoholicCocktailUiState) {
        is AlcoholicCocktailUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is AlcoholicCocktailUiState.Success -> ResultScreen(
            alcoholicCocktailUiState.alcoholic,
            modifier = modifier.fillMaxSize()
        )
        is AlcoholicCocktailUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ResultScreen(cocktailUiState: String, modifier: Modifier = Modifier) {
    Text(
        text = cocktailUiState,
        modifier = modifier
    )
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
       Image(painter = painterResource(id = R.drawable.ic_connection_error),
           contentDescription = "")
        Text(text = stringResource(R.string.loading_failed),
            modifier = modifier.padding(16.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    CocktailsTheme {
    }
}

