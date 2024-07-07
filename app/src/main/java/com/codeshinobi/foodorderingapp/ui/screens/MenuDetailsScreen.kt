package com.codeshinobi.foodorderingapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    var menuItem by remember { mutableStateOf<Document<Map<String, Any>>?>(null) }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(menuItemsService) {
        coroutineScope.launch {
            menuItem = menuItemsService.fetchById(menuItemId)
        }
    }
    if (menuItem != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            )
             {
                menuItem?.data!!["name"]?.toString()?.let {
                    Text(it, fontWeight = FontWeight.Bold)
                }
                Text(menuItem?.data!!["description"].toString(), fontWeight = FontWeight.Light, fontSize = 16.sp, lineHeight = 24.sp,)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text("K " + menuItem?.data!!["price"].toString())
                    Spacer(modifier = Modifier.width(8.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(menuItem?.data!!["type"].toString())
                }

            }
        }
    } else {
        Text("Menu Item not found")
    }
}