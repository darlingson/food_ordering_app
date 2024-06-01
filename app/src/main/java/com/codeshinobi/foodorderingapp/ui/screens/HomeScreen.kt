package com.codeshinobi.foodorderingapp.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import io.appwrite.models.User

@Composable
fun HomeScreen(
    user: MutableState<User<Map<String, Any>>?>
) {
    var name = ""
    if (user != null) {
        name = user.value!!.email
    }
    Text(text = "Hello $name")
}