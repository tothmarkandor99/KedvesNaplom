package com.webtic.kedvesnaplom.ui.details

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.webtic.kedvesnaplom.ui.main.MainPage
import com.webtic.kedvesnaplom.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DetailsPage(viewModel = hiltViewModel())
        }
    }
    // TODO: összerakni Jetpack Compose-zal a szerkesztés/hozzáadás nézetet
}

@Composable
fun DetailsPage(
    viewModel: MainViewModel
){
    Button(onClick = { viewModel. }) {

    }
}