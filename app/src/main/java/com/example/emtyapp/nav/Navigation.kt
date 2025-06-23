package com.example.emtyapp.nav

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.emtyapp.ui.cart.CartScreen
import com.example.emtyapp.ui.cart.ConfirmationScreen
import com.example.emtyapp.ui.product.ProductViewModel
import com.example.emtyapp.ui.product.component.DetailsScreen
import com.example.emtyapp.ui.product.screens.HomeScreen

object Routes {
    const val Home = "home"
    const val ProductDetails = "productDetails"
    const val Cart = "cart"
    const val Confirmation = "confirmation"
}

@Composable
fun AppNavigation(viewModel: ProductViewModel = viewModel()) {
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
                    val product = viewModel.getProductById(productId.toIntOrNull() ?: -1)
                    if (product != null) {
                        viewModel.addToCart(product)
                    }
                },
                onNavigateHome = {
                    navController.popBackStack(Routes.Home, inclusive = false)
                },
                onNavigateToCart = {
                    navController.navigate(Routes.Cart)
                }
            )
        }

        composable(Routes.Cart) {
            CartScreen(
                viewModel = viewModel,
                onCheckout = {
                    navController.navigate(Routes.Confirmation)
                }
            )
        }

        composable(Routes.Confirmation) {
            ConfirmationScreen()
        }
    }
}
