package com.webtic.kedvesnaplom.ui.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.webtic.kedvesnaplom.model.Bejegyzes
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MainPage(
    viewModel: MainViewModel,
    navController: NavController,
) {
    viewModel.refreshBejegyzesek()

    val bejegyzesek: List<Bejegyzes> by viewModel.bejegyzesek.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = bejegyzesek.size.toString() + " bejegyzés") },
                Modifier.background(Color.White),
                actions = {
                    if (viewModel.canAdd.value) {
                        Button(
                            onClick = { navController.navigate(NavScreen.BejegyzesDetails.route) },
                            Modifier.border(BorderStroke(0.dp, Color.Transparent)),
                            elevation = null,
                        ) {
                            Icon(Icons.Rounded.Add, "Új bejegyzés")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(NavScreen.About.route)
            }) {
                Icon(Icons.Rounded.Info, "Névjegy")
            }
        }
    ) {
        if (bejegyzesek.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { viewModel.refreshBejegyzesek() }) {
                    Icon(Icons.Rounded.Refresh, "Frissítés")
                }
            }
        } else {
            Bejegyzesek(
                bejegyzesek,
                viewModel.isLoading,
                onRefresh = { viewModel.refreshBejegyzesek() },
                onDelete = { viewModel.deleteBejegyzes() },
                navController,
            )
        }
    }
}

@Preview
@Composable
fun BejegyzesekPreview() {
    Bejegyzesek(listOf(
        Bejegyzes(
            0,
            "lorem",
            "2022-05-08",
            "The quick brown fox jumps over the lorem ipsum dolor sit amet consectetur"
        ),
        Bejegyzes(
            0,
            "lorem",
            "2022-05-08",
            "The quick brown fox jumps over the lorem ipsum dolor sit amet consectetur"
        ),
        Bejegyzes(
            0,
            "lorem",
            "2022-05-08",
            "The quick brown fox jumps over the lorem ipsum dolor sit amet consectetur"
        )
    ), remember { mutableStateOf(false) }, {}, {}, rememberNavController()
    )
}

@Composable
fun Bejegyzesek(
    bejegyzesek: List<Bejegyzes>,
    isLoading: State<Boolean>,
    onRefresh: () -> Unit,
    onDelete: () -> Unit,
    navController: NavController,
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isLoading.value),
        onRefresh = onRefresh,
    ) {
        LazyColumn(
            Modifier.fillMaxHeight(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        ) {
            val df: DateFormat =
                SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val nowAsIso: String = df.format(Date())
            items(items = bejegyzesek, itemContent = { bejegyzes ->
                if (bejegyzes.datum == nowAsIso) {
                    MutableBejegyzes(
                        bejegyzes,
                        onDelete,
                        onEdit = {
                            navController.navigate("${NavScreen.BejegyzesDetails.route}/${bejegyzes.azonosito}")
                        },
                    )
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
    ImmutableBejegyzes(
        Bejegyzes(
            0,
            "lorem",
            "2022-05-08",
            "The quick brown fox jumps over the lorem ipsum dolor sit amet consectetur"
        )
    )
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
    MutableBejegyzes(
        Bejegyzes(
            0,
            "lorem",
            "2022-05-08",
            "The quick brown fox jumps over the lorem ipsum dolor sit amet consectetur"
        ),
        {},
        {},
    )
}

@Composable
fun MutableBejegyzes(
    bejegyzes: Bejegyzes,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
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
        Row() {
            Button(onClick = { onDelete() }) {
                Icon(Icons.Rounded.Delete, contentDescription = "Törlés")
            }
            Button(onClick = { onEdit() }, Modifier.padding(start = 4.dp)) {
                Icon(Icons.Rounded.Edit, contentDescription = "Szerkesztés")
            }
        }
    }
}
