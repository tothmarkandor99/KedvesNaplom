package com.webtic.kedvesnaplom.ui.about

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.webtic.kedvesnaplom.R

class AboutActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AboutPage()
        }
    }
}

@Preview
@Composable
fun AboutPage() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val image: Painter = painterResource(id = R.drawable.ic_launcher_background)
        Image(
            painter = image,
            contentDescription = "Alkalmazás logo",
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "Kedves naplóm",
            color = Color.Black,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "Tóth Márk Andor, F2S30Y",
            color = Color.Black,
            modifier = Modifier.padding(16.dp)
        )
    }
}
