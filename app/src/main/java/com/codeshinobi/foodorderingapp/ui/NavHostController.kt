//package com.codeshinobi.foodorderingapp.ui
//
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.padding
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import com.codeshinobi.foodorderingapp.ui.screens.HomeScreen
//
//@Composable
//fun NavHostContainer(
//    navController: NavHostController,
//    padding: PaddingValues
//) {
//
//    NavHost(
//        navController = navController,
//
//        // set the start destination as home
//        startDestination = "home",
//
//        // Set the padding provided by scaffold
//        modifier = Modifier.padding(paddingValues = padding),
//
//        builder = {
//
//            // route : Home
//            composable("home") {
//                HomeScreen()
//            }
//
//            // route : search
//            composable("search") {
//                SearchScreen()
//            }
//
//            // route : profile
//            composable("profile") {
//                ProfileScreen()
//            }
//        })
//
//}