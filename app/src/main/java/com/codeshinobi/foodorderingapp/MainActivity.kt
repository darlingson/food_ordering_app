package com.codeshinobi.foodorderingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.codeshinobi.foodorderingapp.services.AccountService
import com.codeshinobi.foodorderingapp.services.Appwrite
import com.codeshinobi.foodorderingapp.ui.screens.UserScreen
import com.codeshinobi.foodorderingapp.ui.theme.FoodOrderingAppTheme
import io.appwrite.models.User
import androidx.compose.material.icons.filled.List
import com.codeshinobi.foodorderingapp.services.Appwrite.ideas
import com.codeshinobi.foodorderingapp.services.IdeaService
import com.codeshinobi.foodorderingapp.ui.screens.IdeasScreen

enum class Screen {
    User, Ideas
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Appwrite.init(applicationContext)

        setContent {
            AppContent(Appwrite.account, Appwrite.ideas)
        }
    }
}
@Composable
private fun AppBottomBar(screen: MutableState<Screen>) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = { screen.value = Screen.Ideas }) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.List, contentDescription = "Ideas")
                    Text("Ideas")
                }
            }
            IconButton(onClick = { screen.value = Screen.User }) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Person, contentDescription = "User")
                    Text("User")
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppContent(accountService: AccountService, ideasService: IdeaService) {
    val user = remember { mutableStateOf<User<Map<String, Any>>?>(null) }
    val screen = remember { mutableStateOf(Screen.Ideas) }

    LaunchedEffect(screen) {
        user.value = accountService.getLoggedIn()
    }

    Scaffold(bottomBar = { AppBottomBar(screen) }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            when (screen.value) {
                Screen.User -> UserScreen(user, accountService)
                else -> IdeasScreen(user.value, ideasService)
            }
        }
    }
}