package com.example.emtyapp.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.NavController
import androidx.navigation.compose.*
import androidx.navigation.navArgument

import com.example.emtyapp.ui.LoginScreen
import com.example.emtyapp.ui.cart.CartScreen
import com.example.emtyapp.ui.cart.ConfirmationScreen
import com.example.emtyapp.ui.product.ProductViewModel
import com.example.emtyapp.ui.product.component.DetailsScreen
import com.example.emtyapp.ui.product.screens.HomeScreen
import com.example.emtyapp.ui.MainScaffold
import androidx.lifecycle.viewmodel.compose.viewModel






object Routes {
    const val Home = "home"
    const val ProductDetails = "productDetails"
    const val Cart = "cart"
    const val Confirmation = "confirmation"
    const val Login = "login"
    const val Register = "register"



}

@Composable
fun AppNavigation(viewModel: ProductViewModel = viewModel()) {
    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route ?: ""

    NavHost(navController = navController, startDestination = Routes.Home) {

        composable(Routes.Home) {
            MainScaffold(navController, currentRoute) {
                HomeScreen(viewModel = viewModel, onNavigateToDetails = { productId ->
                    navController.navigate("${Routes.ProductDetails}/$productId")
                })
            }
        }

        composable(Routes.Cart) {
            MainScaffold(navController, currentRoute) {
                CartScreen(viewModel = viewModel, onCheckout = {
                    navController.navigate(Routes.Confirmation)
                })
            }
        }

        composable(Routes.Login) {
            MainScaffold(navController, currentRoute) {
                LoginScreen(navController) {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Login) { inclusive = true }
                    }
                }
            }
        }

        composable(Routes.Register) {
            MainScaffold(navController, currentRoute) {
                // TODO: Create RegisterScreen
                Text("Register Screen")
            }
        }

        composable(Routes.Confirmation) {
            MainScaffold(navController, currentRoute) {
                ConfirmationScreen(navController)
            }
        }

        composable(
            "${Routes.ProductDetails}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            MainScaffold(navController, currentRoute) {
                DetailsScreen(
                    productId = productId,
                    viewModel = viewModel,
                    onAddToCart = {
                        viewModel.getProductById(productId.toIntOrNull() ?: -1)?.let {
                            viewModel.addToCart(it)
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
        }
    }
}

