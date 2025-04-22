package com.example.fetchrewardexcercise.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fetchrewardexcercise.R
import com.example.fetchrewardexcercise.ui.viewmodel.FilterMode

import com.example.fetchrewardexcercise.ui.viewmodel.ItemViewModel

@Composable
fun MainScreen(viewModel: ItemViewModel) {
    val items by viewModel.filteredItems.collectAsState()
    val filterMode by viewModel.filterMode.collectAsState()

    var inputText by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Spacer(modifier = Modifier.height(40.dp))

        TextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text(stringResource(R.string.filter_by_name)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Sort Options:")
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = filterMode == FilterMode.DEFAULT,
                onClick = { viewModel.updateFilterMode(FilterMode.DEFAULT) }
            )
            Text("Default (Alphabetical)")

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = filterMode == FilterMode.SORT_BY_NUMBER,
                onClick = { viewModel.updateFilterMode(FilterMode.SORT_BY_NUMBER) }
            )
            Text(stringResource(R.string.sort_by_number_in_name))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            viewModel.updateFilter(inputText)
        }) {
            Text(stringResource(R.string.apply_filter))
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items.groupBy { it.listId }
                .toSortedMap()
                .entries
                .forEach { (listId, itemList) ->

                    item {
                        Text(
                            text = "List ID: $listId",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    items(itemList) { item ->
                        var visible by remember { mutableStateOf(false) }

                        LaunchedEffect(Unit) {
                            visible = true
                        }

                        AnimatedVisibility(visible = visible, enter = fadeIn()) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Text(
                                    text = item.name.orEmpty(),
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
        }
    }
}

