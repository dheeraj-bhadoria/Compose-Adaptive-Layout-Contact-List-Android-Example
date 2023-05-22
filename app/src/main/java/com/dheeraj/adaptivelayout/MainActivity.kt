package com.dheeraj.adaptivelayout

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dheeraj.adaptivelayout.ui.theme.AdaptiveLayoutTheme


data class Contact(val id: Int, val name: String, val phoneNumber: String, val imageRes: Int)

val contactList = listOf(
    Contact(1, "John Doe", "123-456-7890", R.drawable.ic_launcher_background),
    Contact(2, "Jane Smith", "987-654-3210", R.drawable.ic_launcher_background),
    Contact(3, "Alex Johnson", "555-123-4567", R.drawable.ic_launcher_background),
    Contact(4, "Celeb David", "4741-123-4567", R.drawable.ic_launcher_background),
    Contact(5, "Rickey", "888-854-5147", R.drawable.ic_launcher_background),
    Contact(6, "Cameron Green", "888-515-8754", R.drawable.ic_launcher_background),
    Contact(7, "Adam Warner", "548-453-5545", R.drawable.ic_launcher_background)

)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdaptiveLayoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun ContactListScreen(onContactClick: (Contact) -> Unit) {
    LazyColumn {
        items(contactList) { contact ->
            ContactItem(contact = contact, onContactClick = onContactClick)
        }
    }
}

@Composable
fun ContactItem(contact: Contact, onContactClick: (Contact) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onContactClick(contact) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = contact.imageRes),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = contact.name, style = MaterialTheme.typography.h6)
            Text(text = contact.phoneNumber, style = MaterialTheme.typography.body1)
        }
    }
}

@Composable
fun ContactDetailScreen(contact: Contact) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = contact.imageRes),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = contact.name, style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = contact.phoneNumber, style = MaterialTheme.typography.body1)
    }
}

@Composable
fun App() {
    val selectedContact = remember { mutableStateOf<Contact?>(null) }
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLandscape) {
            Row(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.weight(1f)) {
                    ContactListScreen(onContactClick = { contact ->
                        selectedContact.value = contact
                    })
                }
                Box(modifier = Modifier.weight(1f)) {
                    selectedContact.value?.let { contact ->
                        ContactDetailScreen(contact = contact)
                    }
                }
            }
        } else {
            if (selectedContact.value == null) {
                ContactListScreen(onContactClick = { contact ->
                    selectedContact.value = contact
                })
            } else {
                ContactDetailScreen(contact = selectedContact.value!!)
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    App()
}