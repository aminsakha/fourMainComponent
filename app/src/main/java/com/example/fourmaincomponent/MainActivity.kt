package com.example.fourmaincomponent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.fourmaincomponent.db.ContactEntity
import com.example.fourmaincomponent.db.DatabaseProvider
import com.example.fourmaincomponent.ui.theme.FourMainComponentTheme
import kotlinx.coroutines.launch

private enum class Screen {
    LIST,
    ADD
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FourMainComponentTheme {
                val appContext = LocalContext.current
                val contactDao = remember { DatabaseProvider.getContactDao(appContext) }
                val coroutineScope = rememberCoroutineScope()

                var currentScreen by remember { mutableStateOf(Screen.LIST) }
                val contacts = remember { mutableStateListOf<ContactEntity>() }

                suspend fun refreshContacts() {
                    val latestContacts = contactDao.getAll()
                    contacts.clear()
                    contacts.addAll(latestContacts)
                }

                LaunchedEffect(Unit) {
                    refreshContacts()
                }

                when (currentScreen) {
                    Screen.LIST -> ContactsListScreen(
                        contacts = contacts,
                        onAddContact = { currentScreen = Screen.ADD },
                        onDeleteContact = { contact ->
                            coroutineScope.launch {
                                contactDao.delete(contact)
                                refreshContacts()
                            }
                        }
                    )

                    Screen.ADD -> AddContactScreen(
                        onSave = { name, phone ->
                            coroutineScope.launch {
                                contactDao.insert(
                                    ContactEntity(
                                        name = name,
                                        phone = phone
                                    )
                                )
                                refreshContacts()
                                currentScreen = Screen.LIST
                            }
                        },
                        onBack = { currentScreen = Screen.LIST }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContactsListScreen(
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
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
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

@Composable
private fun ContactRow(
    contact: ContactEntity,
    onDelete: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = 1.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = contact.phone,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            TextButton(onClick = onDelete) {
                Text(text = "Delete")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddContactScreen(
    onSave: (String, String) -> Unit,
    onBack: () -> Unit
) {
    var contactName by remember { mutableStateOf("") }
    var contactPhone by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Contact") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = contactName,
                onValueChange = { contactName = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = contactPhone,
                onValueChange = { contactPhone = it },
                label = { Text("Phone") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    onSave(contactName, contactPhone)
                    contactName = ""
                    contactPhone = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}