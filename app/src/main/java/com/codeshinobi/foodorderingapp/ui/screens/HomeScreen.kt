package com.codeshinobi.foodorderingapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.codeshinobi.foodorderingapp.services.MenuItemService
import io.appwrite.models.Document
import io.appwrite.models.User
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavHostController,
    user: MutableState<User<Map<String, Any>>?>,
    menuItemsService: MenuItemService
) {
    var name = ""
    val numbers = (0..20).toList()
    var menuitems by remember { mutableStateOf<List<Document<Map<String, Any>>>>(listOf()) }
    val coroutineScope = rememberCoroutineScope()
    var search by remember { mutableStateOf("") }
    LaunchedEffect(menuItemsService) {
        coroutineScope.launch {
            menuitems = menuItemsService.fetch()
        }
    }
    if (user.value != null) {
        name = user.value!!.email!!
        Log.d(user.toString(), user.value!!.email)
        Column {
            Text(text = "Hello $name")
            TextField(
                value =search ,
                onValueChange = { search = it },
                label = { Text(text = "Search") },
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            )
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(menuitems.size) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Card(
                            modifier = Modifier.size(
                                width = 150.dp,
                                height = 150.dp)
                                .padding(10.dp),
                            shape = CardDefaults.elevatedShape
                        ) {
                            menuitems[it].data["title"]?.toString()?.let {
                                Text(text = it,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                            menuitems[it].data["description"]?.toString()?.let {
                                Text(text = it,maxLines = 4)
                            }
                            menuitems[it].data["price"]?.toString()?.let {
                                Text(text = "K $it",
                                    style = TextStyle(
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }else if (user.value == null) {
        Column {
            Button(onClick = {
                navController.navigate("login")
            }) {
                Text(text = "Login")
            }
        }
    }
}