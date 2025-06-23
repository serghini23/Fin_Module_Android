package com.example.emtyapp.nav

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.emtyapp.ui.product.ProductViewModel
import com.example.emtyapp.ui.product.component.DetailsScreen
import com.example.emtyapp.ui.product.screens.HomeScreen
import com.example.emtyapp.ui.cart.CartScreen


object Routes {
    const val Home = "home"
    const val ProductDetails = "productDetails"
    const val Cart = "cart"
}

@Composable
fun AppNavigation(viewModel: ProductViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Home) {

        composable(Routes.Home) {
            HomeScreen(viewModel, onNavigateToDetails = { productId ->
                navController.navigate("${Routes.ProductDetails}/$productId")
            })
        }

        composable(
            "${Routes.ProductDetails}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            DetailsScreen(
                productId = productId,
                viewModel = viewModel,
                onAddToCart = {
                },
                onNavigateHome = {
                },
                onNavigateToCart = {
                    navController.navigate("${Routes.Cart}/$productId")
                }
            )
        }

        composable(
            "${Routes.Cart}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            CartScreen(productId = productId, viewModel = viewModel)
        }
    }
}
