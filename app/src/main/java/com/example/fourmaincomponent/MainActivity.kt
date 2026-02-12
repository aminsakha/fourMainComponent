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
import androidx.compose.runtime.saveable.rememberSaveable
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
                ContactsApp()
            }
        }
    }
}

@Composable
private fun ContactsApp() {
    val appContext = LocalContext.current
    val contactDao = remember { DatabaseProvider.getContactDao(appContext) }
    val coroutineScope = rememberCoroutineScope()

    var currentScreen by rememberSaveable { mutableStateOf(Screen.LIST) }
    val contacts = remember { mutableStateListOf<ContactEntity>() }

    suspend fun refreshContacts() {
        contacts.clear()
        contacts.addAll(contactDao.getAll())
    }

    LaunchedEffect(contactDao) {
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
            onSave = { contactName, phoneNumber ->
                coroutineScope.launch {
                    contactDao.insert(ContactEntity(name = contactName, phone = phoneNumber))
                    refreshContacts()
                    currentScreen = Screen.LIST
                }
            },
            onBack = { currentScreen = Screen.LIST }
        )
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
            modifier = Modifier.fillMaxSize().padding(innerPadding)
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
    ListItem(
        headlineContent = { Text(text = contact.name) },
        supportingContent = { Text(text = contact.phone) },
        trailingContent = {
            TextButton(onClick = onDelete) {
                Text(text = "Delete")
            }
        }
    )
    Divider()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddContactScreen(
    onSave: (String, String) -> Unit,
    onBack: () -> Unit
) {
    var contactName by rememberSaveable { mutableStateOf("") }
    var contactPhone by rememberSaveable { mutableStateOf("") }

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

            ContactTextField(
                label = "Name",
                value = contactName,
                onValueChange = { contactName = it }
            )

            ContactTextField(
                label = "Phone",
                value = contactPhone,
                keyboardType = KeyboardType.Phone,
                onValueChange = { contactPhone = it }
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

@Composable
private fun ContactTextField(
    label: String,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth()
    )
}