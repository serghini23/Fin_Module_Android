package com.example.emtyapp.ui
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.emtyapp.nav.Routes

@Composable
fun MainScaffold(
    navController: NavController,
    currentRoute: String,
    content: @Composable () -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {
        NavigationRail {
            NavigationRailItem(
                selected = currentRoute == Routes.Home,
                onClick = { navController.navigate(Routes.Home) },
                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                label = { Text("Accueil") }
            )
            NavigationRailItem(
                selected = currentRoute == Routes.Cart,
                onClick = { navController.navigate(Routes.Cart) },
                icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") },
                label = { Text("Panier") }
            )
            NavigationRailItem(
                selected = currentRoute == Routes.Login,
                onClick = { navController.navigate(Routes.Login) },
                icon = { Icon(Icons.Default.Person, contentDescription = "Login") },
                label = { Text("Login") }
            )
            NavigationRailItem(
                selected = currentRoute == Routes.Register,
                onClick = { navController.navigate(Routes.Register) },
                icon = { Icon(Icons.Default.PersonAdd, contentDescription = "Register") },
                label = { Text("Register") }
            )
        }

        // Your screen content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            content()
        }
    }
}
