package com.example.emtyapp.ui.product.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.emtyapp.ui.product.ProductViewModel

@Composable
fun DetailsScreen(
    productId: String,
    viewModel: ProductViewModel,
    onAddToCart: () -> Unit,
    onNavigateHome: () -> Unit,
    onNavigateToCart: () -> Unit
) {
    val product = remember(productId) {
        viewModel.getProductById(productId.toIntOrNull() ?: -1)
    }

    val isAddedToCartState = remember { mutableStateOf(false) }
    val isAddedToCart = isAddedToCartState.value

    if (product == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("❌ Produit introuvable", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text("💰 ${product.price} $", style = MaterialTheme.typography.titleMedium)
                Text("📂 ${product.category}")
                Text("⭐ ${product.rating.rate} (${product.rating.count} avis)")

                Spacer(modifier = Modifier.height(24.dp))

                if (!isAddedToCart) {
                    Button(onClick = {
                        onAddToCart()
                        isAddedToCartState.value = true
                    }) {
                        Text("Ajouter au Panier 🛒")
                    }
                } else {
                    Text("✅ Ajouté au panier", color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = onNavigateHome) {
                            Text("🏠 Accueil")
                        }
                        Button(onClick = onNavigateToCart) {
                            Text("🛒 Voir Panier")
                        }
                    }
                }
            }
        }
    }
}
