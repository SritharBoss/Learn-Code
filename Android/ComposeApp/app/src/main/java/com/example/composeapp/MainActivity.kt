package com.example.composeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.composeapp.ui.theme.ComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAppTheme {
                Surface(color = MaterialTheme.colorScheme.surface, modifier = Modifier.fillMaxSize()) {
                    val list = listOf(
                        Person("Srithar", 26),
                        Person("SritharBoss", 28),
                        Person("Srithar", 26)
                    )
                    Conversation(list)
                }
            }
        }
    }

    data class Person(var name: String, var age: Int)

    @Composable
    fun MessageCard(person: Person) {
        Row(modifier = Modifier.padding(all = 5.dp)) {
            Image(
                painter = painterResource(id = R.drawable.profile_picture),
                contentDescription = "Icon",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(5.dp))

            var isExpanded by remember { mutableStateOf(false) }
            Column {
                Text(text = person.name, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Surface(shape = MaterialTheme.shapes.medium, shadowElevation = 5.dp) {
                    Text(
                        text = person.age.toString(),
                        modifier = Modifier.padding(all = 4.dp),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }

    @Composable
    fun Conversation(messages: List<Person>) {
        LazyColumn {
            items(messages) { a ->
                MessageCard(a)
            }
        }
    }
}