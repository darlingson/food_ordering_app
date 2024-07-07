package com.codeshinobi.foodorderingapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.codeshinobi.foodorderingapp.services.MenuItemService
import io.appwrite.models.Document
import io.appwrite.models.User
import kotlinx.coroutines.launch

@Composable
fun MenuDetailsScreen(
    menuItemId: String,
    navController: NavHostController,
    user: MutableState<User<Map<String, Any>>?>,
    menuItemsService: MenuItemService
) {
    var menuItem by remember{mutableStateOf<Document<Map<String, Any>>?>(null)}
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(menuItemsService) {
        coroutineScope.launch {
            menuItem = menuItemsService.fetchById(menuItemId)
        }
    }
    if (menuItem != null) {
        Column {
            menuItem?.data!!["name"]?.toString()?.let {
                Text(it)
            }
        }
    }
    else {
        Text("Menu Item not found")
    }
}