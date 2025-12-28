package com.bp.bucketlist.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bp.bucketlist.data.BucketItem
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEditItemDialog(
    item: BucketItem?,
    onDismiss: () -> Unit,
    onSave: (BucketItem) -> Unit
) {
    var title by remember { mutableStateOf(item?.title ?: "") }
    var description by remember { mutableStateOf(item?.description ?: "") }
    var category by remember { mutableStateOf(item?.category ?: "") }
    var targetDate by remember { mutableStateOf(item?.targetDate ?: "") }
    var priority by remember {
        mutableStateOf(item?.priority?.toString() ?: "1")
    }
    val isTargetDateValid = remember(targetDate) {
        try {
            LocalDate.parse(targetDate)
            true
        } catch (e: Exception) {
            false
        }
    }


    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(if (item == null) "Add Bucket Item" else "Edit Bucket Item")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title*") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = targetDate,
                    onValueChange = { targetDate = it },
                    label = { Text("Target Date (yyyy-MM-dd)*") },
                    isError = targetDate.isNotBlank() && !isTargetDateValid,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = priority,
                    onValueChange = {
                        priority = it.filter { c -> c.isDigit() }
                    },
                    label = { Text("Priority (1–5)*") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val prio = priority.toIntOrNull()?.coerceIn(1, 5) ?: 1

                    val savedItem =
                        if (item == null) {
                            BucketItem(
                                title = title,
                                description = description,
                                category = category,
                                targetDate = targetDate,
                                priority = prio
                            )
                        } else {
                            item.copy(
                                title = title,
                                description = description,
                                category = category,
                                targetDate = targetDate,
                                priority = prio
                            )
                        }

                    onSave(savedItem)
                },
                enabled = title.isNotBlank() && isTargetDateValid
            ) {
                Text(if (item == null) "Add" else "Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}