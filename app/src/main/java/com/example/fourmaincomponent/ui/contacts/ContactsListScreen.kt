package com.example.fourmaincomponent.ui.contacts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fourmaincomponent.db.ContactEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsListScreen(
    contacts: List<ContactEntity>,
    onAddContact: () -> Unit,
    onDeleteContact: (ContactEntity) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Contacts") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddContact
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Contact")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(contacts) { contact ->
                ContactRow(
                    contact = contact,
                    onDelete = { onDeleteContact(contact) }
                )
            }
        }
    }
}
