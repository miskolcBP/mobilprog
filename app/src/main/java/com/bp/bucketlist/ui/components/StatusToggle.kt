package com.bp.bucketlist.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatusToggle(
    showingCompleted: Boolean,
    onToggle: (Boolean) -> Unit
) {
    SingleChoiceSegmentedButtonRow {
        SegmentedButton(
            selected = !showingCompleted,          // Active button
            onClick = { onToggle(false) },
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Active")
        }

        Spacer(modifier = Modifier.width(12.dp))

        SegmentedButton(
            selected = showingCompleted,           // Completed button
            onClick = { onToggle(true) },
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Completed")
        }
    }
}