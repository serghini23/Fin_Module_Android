package com.example.emtyapp.ui.cart

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.emtyapp.ui.product.ProductViewModel

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.emtyapp.data.Entities.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Composable
fun CartScreen(
    viewModel: ProductViewModel,
    onCheckout: () -> Unit
) {
    val cartItems by viewModel.cartItems.collectAsState()

    if (cartItems.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("🛒 Le panier est vide", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        val totalPrice = cartItems.sumOf { it.price }
        val itemCount = cartItems.size

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                "🛒 Panier ($itemCount articles)",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))

            cartItems.forEach { product ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("📦 ${product.title}", style = MaterialTheme.typography.titleMedium)
                        Text("💰 ${product.price} $", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Button(
                            onClick = { viewModel.removeFromCart(product.id) },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("❌ Supprimer")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                "💵 Total : $totalPrice $",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onCheckout,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("✅ Passer à la caisse")
            }
        }
    }
}

