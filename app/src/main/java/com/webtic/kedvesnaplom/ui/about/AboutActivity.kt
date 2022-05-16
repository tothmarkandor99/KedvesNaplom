package com.webtic.kedvesnaplom.ui.about

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val image: Painter = painterResource(id = R.drawable.ic_launcher_background)
        Image(painter = image,contentDescription = "")
        Text(
            text = "Second item",
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "Third item",
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(16.dp)
        )
    }
}
