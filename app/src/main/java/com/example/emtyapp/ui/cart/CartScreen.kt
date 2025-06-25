package com.example.emtyapp.ui.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.emtyapp.ui.product.ProductViewModel

@Composable
fun CartScreen(
    viewModel: ProductViewModel,
    onCheckout: () -> Unit
) {
    val cartItems by viewModel.cartItems.collectAsState()
    val quantities = remember(cartItems) {
        mutableStateMapOf<Int, Int>().apply {
            cartItems.forEach { put(it.id, getOrDefault(it.id, 1)) }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (cartItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("🛒 Le panier est vide", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            val totalPrice = cartItems.sumOf { it.price * (quantities[it.id] ?: 1) }
            val itemCount = cartItems.sumOf { quantities[it.id] ?: 1 }

            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxSize()
            ) {
                Text(
                    "🛒 Panier ($itemCount articles)",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    cartItems.forEach { product ->
                        val quantity = quantities[product.id] ?: 1

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("📦 ${product.title}", style = MaterialTheme.typography.titleMedium)
                                Text("💰 ${product.price} $ / unité", style = MaterialTheme.typography.bodyMedium)

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    IconButton(onClick = {
                                        if (quantity > 1) {
                                            quantities[product.id] = quantity - 1
                                        }
                                    }) {
                                        Icon(Icons.Default.Remove, contentDescription = "Diminuer")
                                    }

                                    Text("$quantity", style = MaterialTheme.typography.titleMedium)

                                    IconButton(onClick = {
                                        quantities[product.id] = quantity + 1
                                    }) {
                                        Icon(Icons.Default.Add, contentDescription = "Augmenter")
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    "🧾 Total pour cet article: ${product.price * quantity} $",
                                    style = MaterialTheme.typography.bodyLarge
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Button(
                                    onClick = { viewModel.removeFromCart(product.id) },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                                    modifier = Modifier.align(Alignment.End)
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = "Supprimer")
                                    Spacer(Modifier.width(6.dp))
                                    Text("Supprimer")
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "💵 Total à payer : $totalPrice $",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        viewModel.placeOrder()
                        onCheckout()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("✅ Passer à la caisse")
                }

            }
    }
}
}
