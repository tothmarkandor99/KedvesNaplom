package com.webtic.kedvesnaplom.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.webtic.kedvesnaplom.R
import com.webtic.kedvesnaplom.model.Bejegyzes
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainPage(viewModel = hiltViewModel(), {})
        }
    }
}

@Preview
@Composable
fun BejegyzesekPreview() {
    Bejegyzesek(listOf(
        Bejegyzes(0,"lorem","2022-05-08", "The quick brown fox jumps over the lorem ipsum dolor sit amet consectetur"),
        Bejegyzes(0,"lorem","2022-05-08", "The quick brown fox jumps over the lorem ipsum dolor sit amet consectetur"),
        Bejegyzes(0,"lorem","2022-05-08", "The quick brown fox jumps over the lorem ipsum dolor sit amet consectetur")
    ), remember { mutableStateOf(false) }, {}, {})
}

@Composable
fun MainPage(
    viewModel: MainViewModel,
    selectBejegyzes: (Long) -> Unit
) {
    val bejegyzesek: List<Bejegyzes> by viewModel.bejegyzesek.collectAsState()
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = bejegyzesek.size.toString() + " bejegyzés") },
            Modifier.background(Color.White)
        )
    }) {
        if (bejegyzesek.isEmpty()) {
            Button(onClick = { viewModel.refreshBejegyzesek() }) {
                Text(text = "Frissítés")
            }
        } else {
            Bejegyzesek(
                bejegyzesek,
                viewModel.isLoading,
                { viewModel.refreshBejegyzesek() },
                onDelete = { viewModel.deleteBejegyzes() }
            )
        }
    }
}

@Composable
fun Bejegyzesek(
    bejegyzesek: List<Bejegyzes>,
    isLoading: State<Boolean>,
    onRefresh: () -> Unit,
    onDelete: ()-> Unit,
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isLoading.value),
        onRefresh = onRefresh,
    ) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        ) {
            val df: DateFormat =
                SimpleDateFormat("yyyy-MM-dd")
            val nowAsIso: String = df.format(Date())
            items(items = bejegyzesek, itemContent = { bejegyzes ->
                Log.d("KN", "bejegyzes.datum: " + bejegyzes.datum)
                if (bejegyzes.datum.equals(nowAsIso)) {
                    Log.d("KN", "nowAsIso: "  + nowAsIso)
                    MutableBejegyzes(bejegyzes, onDelete)
                } else {
                    ImmutableBejegyzes(bejegyzes)
                }
                Divider(Modifier.padding(vertical = 16.dp))
            })
        }
    }
}

@Preview
@Composable
fun ImmutableBejegyzesPreview() {
    ImmutableBejegyzes(Bejegyzes(0,"lorem","2022-05-08", "The quick brown fox jumps over the lorem ipsum dolor sit amet consectetur"))
}

@Composable
fun ImmutableBejegyzes(
    bejegyzes: Bejegyzes
) {
    Column() {
        Text(
            text = bejegyzes.datum,
            Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = bejegyzes.tartalom,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun MutableBejegyzesPreview() {
    MutableBejegyzes(Bejegyzes(
        0,
        "lorem","2022-05-08", "The quick brown fox jumps over the lorem ipsum dolor sit amet consectetur"),
        {},
    )
}

@Composable
fun MutableBejegyzes(
    bejegyzes: Bejegyzes,
    onDelete: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement  =  Arrangement.SpaceBetween
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = bejegyzes.datum,
                Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = bejegyzes.tartalom,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
        val image: Painter = painterResource(id = R.drawable.ic_launcher_background)
        Row() {
            Button(onClick = { onDelete() }) {
                Icon(Icons.Rounded.Delete, contentDescription = "Törlés")
            }
            Button(onClick = { /*TODO*/ }, Modifier.padding(start = 4.dp)) {
                Icon(Icons.Rounded.Edit, contentDescription = "Szerkesztés")
            }
        }
    }
}