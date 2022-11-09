package com.iulian.iancu.cocktailapp.ui.main

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.iulian.iancu.cocktailapp.R
import com.iulian.iancu.cocktailapp.ui.theme.CocktailAppTheme
import com.iulian.iancu.data.DrinksRepositoryImpl
import com.iulian.iancu.data.DrinksService
import com.iulian.iancu.domain.GetDrinksUseCase
import com.iulian.iancu.entity.Cocktail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private var drinks = emptyList<Cocktail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.state.observe(this, ::onStateChange)
        setContent {
            Content()
        }
        viewModel.getDrinks()
    }

    private fun onStateChange(state: State?) {
        if (state == null) return
        when (state.error) {
            Error.Network ->
                Toast.makeText(this, R.string.error_network, Toast.LENGTH_SHORT).show()
            Error.Unknown ->
                Toast.makeText(this, R.string.error_unknown, Toast.LENGTH_SHORT).show()
            else -> {
                //TODO maybe some analytics
            }
        }

        state.drinks?.let { drinks = it }

        setContent {
            Content()
        }
    }

    @Preview
    @Composable
    fun Content() {
        val imageLoader = ImageLoader.Builder(this)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .error(getDrawable(R.drawable.ic_baseline_error_outline_24))
            .build()

        CocktailAppTheme {
            Surface(color = MaterialTheme.colors.background) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
                ) {
                    items(drinks) {
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            val openDialog = remember { mutableStateOf(false) }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        openDialog.value = !openDialog.value
                                    }
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    AsyncImage(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(4.dp),
                                        placeholder = painterResource(R.drawable.ic_baseline_emoji_emotions_24),
                                        model = it.image,
                                        contentDescription = null,
                                        alignment = Alignment.Center,
                                        imageLoader = imageLoader
                                    )
                                    Text(text = it.name, Modifier.padding(4.dp))
                                    if (openDialog.value) {
                                        Text(
                                            text = it.instructions,
                                            Modifier.padding(4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}