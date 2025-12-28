@file:OptIn(ExperimentalMaterial3Api::class)

package com.bp.bucketlist.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bp.bucketlist.data.BucketItem
import com.bp.bucketlist.ui.components.StatusToggle
import kotlinx.coroutines.launch
import com.bp.bucketlist.ui.viewmodel.BucketViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(viewModel: BucketViewModel) {
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    var showingCompleted by remember { mutableStateOf(false) }
    var editingItem by remember { mutableStateOf<BucketItem?>(null) }

    val items by viewModel.items.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadActive()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (showingCompleted) "Completed Items" else "Bucket List") },
                actions = {
                    var showingCompleted by remember { mutableStateOf(false) }

                    StatusToggle(
                        showingCompleted = showingCompleted,
                        onToggle = {
                            showingCompleted = it
                            if (it) viewModel.loadCompleted() else viewModel.loadActive()
                        }
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Text("+")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (items.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("No bucket items yet")
                    Text("Tap + to add your first goal")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items) { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(item.title, style = MaterialTheme.typography.titleMedium)
                                Text(item.description, style = MaterialTheme.typography.bodyMedium)
                                Spacer(modifier = Modifier.height(12.dp))
                                if (item.category != "") {
                                    Text(text = "\uD83C\uDFF7\uFE0F ${item.category}")
                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                                Text(text = "❗  ${item.priority}")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "\uD83C\uDFAF  ${item.targetDate}")

                                if (item.status && item.completedAt != null) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(" ✔  ${item.completedAt}")
                                }

                                Row(
                                    horizontalArrangement = Arrangement.End,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    if (!item.status) {
                                        IconButton(onClick = {
                                            scope.launch {
                                                viewModel.markCompleted(item.id)
                                            }
                                        }) {
                                            Text("✔")
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    IconButton(onClick = {
                                        scope.launch {
                                            viewModel.delete(item)
                                        }
                                    }) {
                                        Text("\uD83D\uDDD1")
                                    }

                                    IconButton(onClick = {
                                        editingItem = item
                                        showDialog = true
                                    }) {
                                        Text("✏\uFE0F")
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }
    if (showDialog) {
        AddEditItemDialog(
            item = editingItem,
            onDismiss = {
                showDialog = false
                editingItem = null
            },
            onSave = { item ->
                if (editingItem == null) {
                    viewModel.add(item)
                } else {
                    viewModel.update(item)
                }
                showDialog = false
                editingItem = null
            }
        )
    }
}