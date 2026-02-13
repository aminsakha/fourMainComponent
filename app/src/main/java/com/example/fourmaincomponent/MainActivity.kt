package com.example.fourmaincomponent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.fourmaincomponent.db.ContactEntity
import com.example.fourmaincomponent.db.DatabaseProvider
import com.example.fourmaincomponent.ui.contacts.AddContactScreen
import com.example.fourmaincomponent.ui.contacts.ContactsListScreen
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
