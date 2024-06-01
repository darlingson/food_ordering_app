package com.codeshinobi.foodorderingapp

import BottomNavigationItem
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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.codeshinobi.foodorderingapp.services.AccountService
import com.codeshinobi.foodorderingapp.services.Appwrite
import com.codeshinobi.foodorderingapp.ui.screens.UserScreen
import io.appwrite.models.User
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codeshinobi.foodorderingapp.models.Screens
import com.codeshinobi.foodorderingapp.services.IdeaService
import com.codeshinobi.foodorderingapp.ui.screens.HomeScreen
import com.codeshinobi.foodorderingapp.ui.screens.IdeasScreen

enum class Screen {
    Home, User, Ideas
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Appwrite.init(applicationContext)

        setContent {
//            AppContent(Appwrite.account, Appwrite.ideas)
            BottomNavigationBar(Appwrite.account, Appwrite.ideas)
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
            IconButton(onClick = { screen.value = Screen.Home }) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Home, contentDescription = "Home")
                    Text("Home")
                }
            }
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


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun AppContent(accountService: AccountService, ideasService: IdeaService) {
//    val user = remember { mutableStateOf<User<Map<String, Any>>?>(null) }
//    val screen = remember { mutableStateOf(Screen.Ideas) }
//
//    LaunchedEffect(screen) {
//        user.value = accountService.getLoggedIn()
//    }
//
//    Scaffold(bottomBar = { AppBottomBar(screen) }) { padding ->
//        Column(modifier = Modifier.padding(padding)) {
//            when (screen.value) {
//                Screen.Home -> HomeScreen(user)
//                Screen.User -> UserScreen(user, accountService)
//                else -> IdeasScreen(user.value, ideasService)
//            }
//        }
//    }
//}
@Composable
fun BottomNavigationBar(accountService: AccountService, ideasService: IdeaService) {
    var navigationSelectedItem by remember {
        mutableStateOf(0)
    }
    val navController = rememberNavController()
    val user = remember { mutableStateOf<User<Map<String, Any>>?>(null) }
    val screen = remember { mutableStateOf(Screen.Ideas) }

    LaunchedEffect(screen) {
        user.value = accountService.getLoggedIn()
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                BottomNavigationItem().bottomNavigationItems().forEachIndexed {index,navigationItem ->
                    NavigationBarItem(
                        selected = index == navigationSelectedItem,
                        label = {
                            Text(navigationItem.label)
                        },
                        icon = {
                            Icon(
                                navigationItem.icon,
                                contentDescription = navigationItem.label
                            )
                        },
                        onClick = {
                            navigationSelectedItem = index
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) {paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screens.Home.route,
                modifier = Modifier.padding(paddingValues = paddingValues)
            ) {
                composable(Screens.Home.route) {
                    HomeScreen(navController, user)
                }
                composable(Screens.Search.route) {
                    IdeasScreen(user.value, ideasService, navController)
                }
                composable(Screens.Profile.route) {
                    UserScreen(user, accountService, navController)
                }
            }
    }
}
