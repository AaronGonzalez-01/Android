package com.example.shoppingapplication.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shoppingapplication.ui.screens.AddItemScreen
import com.example.shoppingapplication.ui.screens.ShoppingListScreen
import com.example.shoppingapplication.viewmodel.ShoppingViewModel

sealed class Screen(val route: String) {
    object List : Screen("list")
    object Add : Screen("add/{title}") {
        fun createRoute(title: String) = "add/$title"
    }
}

@Composable
fun ShoppingNavHost(viewModel: ShoppingViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.List.route
    ) {
        composable(Screen.List.route) {
            ShoppingListScreen(
                viewModel = viewModel,
                onAddClick = {
                    navController.navigate(Screen.Add.createRoute("Nuevo item"))
                }
            )
        }

        composable(
            route = Screen.Add.route,
            arguments = listOf(
                navArgument("title") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: "Nuevo item"
            AddItemScreen(
                title = title,
                onSave = { name ->
                    viewModel.addItem(name)
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}


