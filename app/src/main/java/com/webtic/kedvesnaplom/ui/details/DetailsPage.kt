package com.webtic.kedvesnaplom.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.webtic.kedvesnaplom.model.Bejegyzes

@Composable
fun DetailsPage(
    viewModel: DetailsViewModel,
    navController: NavController,
    azonosito: Int?,
) {
    val bejegyzes: Bejegyzes? by viewModel.bejegyzes.collectAsState()
    viewModel.loadBejegyzes(azonosito)

    bejegyzes?.let { b ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = b.datum) },
                    Modifier.background(Color.White),
                )
            }
        ) {
            EditBejegyzes(
                initialTartalom = b.tartalom,
                onCancel = { navController.popBackStack() },
                onSave = {
                    viewModel.saveBejegyzes(it)
                    navController.popBackStack()
                }
            )
        }
    }
}

@Preview
@Composable
fun EditBejegyzesPreview() {
    EditBejegyzes("Lorem ipsum dolor sit amet", {}, {})
}

@Composable
fun EditBejegyzes(
    initialTartalom: String,
    onCancel: () -> Unit,
    onSave: (tartalom: String) -> Unit,
) {
    var tartalom by rememberSaveable { mutableStateOf(initialTartalom) }

    Column(Modifier.fillMaxHeight().fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp).weight(1f)
        ) {
            TextField(
                value = tartalom,
                onValueChange = { newValue -> tartalom = newValue },
                Modifier.fillMaxWidth().fillMaxHeight()
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().background(Color.Cyan).padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Button(onClick = { onCancel() }) {
                Text("Vissza")
            }
            Button(onClick = { onSave(tartalom) }) {
                Text("Ment??s")
            }
        }
    }
}
