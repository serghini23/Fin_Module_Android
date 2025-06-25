package com.example.emtyapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.emtyapp.UserPrefs
import com.example.emtyapp.nav.Routes
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.History


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    navController: NavController,
    currentRoute: String,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val userPrefs = remember { UserPrefs(context) }
    val isLoggedIn by userPrefs.isLoggedIn.collectAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    var isSidebarVisible by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxSize()) {

        if (isSidebarVisible) {
            NavigationRail(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(72.dp) // optional: fixed width
            ) {
                NavigationRailItem(
                    selected = currentRoute == Routes.Home,
                    onClick = { navController.navigate(Routes.Home) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Accueil") }
                )
                NavigationRailItem(
                    selected = currentRoute == Routes.OrderHistory,
                    onClick = { navController.navigate(Routes.OrderHistory) },
                    icon = { Icon(Icons.Default.History, contentDescription = "Historique des commandes") },
                    label = { Text("Historique") }
                )

                NavigationRailItem(
                    selected = currentRoute == Routes.Cart,
                    onClick = { navController.navigate(Routes.Cart) },
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Panier") },
                    label = { Text("Panier") }
                )

                if (isLoggedIn) {
                    NavigationRailItem(
                        selected = false,
                        onClick = {
                            coroutineScope.launch {
                                userPrefs.logout()
                                navController.navigate(Routes.Login) {
                                    popUpTo(Routes.Home) { inclusive = true }
                                }
                            }
                        },
                        icon = { Icon(Icons.Default.ExitToApp, contentDescription = "Logout") },
                        label = { Text("Logout" +
                                "") }
                    )
                } else {
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
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            TopAppBar(
                title = { Text("Serghini Store") },
                navigationIcon = {
                    IconButton(onClick = { isSidebarVisible = !isSidebarVisible }) {
                        Icon(Icons.Default.Menu, contentDescription = "Toggle Sidebar")
                    }
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                content()
            }
        }
    }
}
