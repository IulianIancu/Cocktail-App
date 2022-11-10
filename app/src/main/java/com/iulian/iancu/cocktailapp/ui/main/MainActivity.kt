package com.iulian.iancu.cocktailapp.ui.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.iulian.iancu.cocktailapp.R
import com.iulian.iancu.cocktailapp.ui.theme.CocktailAppTheme
import com.iulian.iancu.entity.Cocktail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private var drinks = emptyList<Cocktail>()
    private val textState = mutableStateOf(TextFieldValue(""))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.state.observe(this, ::onStateChange)
        setContent {
            Scaffold(
                topBar = { TopBar() },
            ) { padding -> // We need to pass scaffold's inner padding to content. That's why we use Box.
                Box(modifier = Modifier.padding(padding)) {
                    Content()
                }
            }
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
            Scaffold(
                topBar = { TopBar() },
                backgroundColor = colorResource(id = R.color.black)
            ) { padding -> // We need to pass scaffold's inner padding to content. That's why we use Box.
                Box(modifier = Modifier.padding(padding)) {
                    Content()
                }
            }

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

                                        LazyColumn(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .requiredHeight(Dp(100f)),
                                            contentPadding = PaddingValues(
                                                horizontal = 16.dp,
                                                vertical = 8.dp
                                            )
                                        ) {
                                            items(it.ingredients) { item ->
                                                Text(
                                                    text = "${item.first} ${item.second}",
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
                SearchView(textState)
            }
        }
    }

    @Composable
    fun TopBar() {
        TopAppBar(
            title = { Text(text = stringResource(R.string.app_name), fontSize = 18.sp) },
            contentColor = Color(R.color.white)
        )
    }

    @Composable
    fun SearchView(state: MutableState<TextFieldValue>) {
        TextField(
            value = state.value,
            onValueChange = { value ->
                state.value = value
                viewModel.getDrinks(value.text)
            },
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = TextStyle(fontSize = 18.sp),
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                )
            },
            trailingIcon = {
                if (state.value != TextFieldValue("")) {
                    IconButton(
                        onClick = {
                            state.value =
                                TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(15.dp)
                                .size(24.dp)
                        )
                    }
                }
            },
            singleLine = true,
            shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                cursorColor = Color.White,
                leadingIconColor = Color.White,
                trailingIconColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}