package com.codeshinobi.foodorderingapp.models

import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val route : String) {
    object Home : Screens("home_route")
    object Search : Screens("search_route")
    object Profile : Screens("profile_route")
}