package com.codeshinobi.foodorderingapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.appwrite.models.User

@Composable
fun HomeScreen(
    navController: NavHostController,
    user: MutableState<User<Map<String, Any>>?>
) {
    var name = ""
    val numbers = (0..20).toList()
    if (user.value != null) {
        name = user.value!!.email!!
        Log.d(user.toString(), user.value!!.email)
        Column {
            Text(text = "Hello $name")
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 200.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(numbers.size) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Card(
                            modifier = Modifier.size(width = 150.dp, height = 100.dp),
                            shape = CardDefaults.elevatedShape
                        ) {
                            Text(text = "Number")
                            Text(text = "  $it",)
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