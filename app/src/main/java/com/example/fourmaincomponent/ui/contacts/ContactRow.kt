package com.example.fourmaincomponent.ui.contacts

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.fourmaincomponent.db.ContactEntity

@Composable
fun ContactRow(
    contact: ContactEntity,
    onDelete: () -> Unit
) {
    ListItem(
        headlineContent = { Text(text = contact.name) },
        supportingContent = { Text(text = contact.phone) },
        trailingContent = {
            TextButton(onClick = onDelete) {
                Text(text = "Delete")
            }
        }
    )
    HorizontalDivider()
}
