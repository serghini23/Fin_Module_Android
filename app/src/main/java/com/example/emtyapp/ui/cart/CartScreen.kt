package com.example.emtyapp.ui.cart

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.example.emtyapp.ui.product.ProductViewModel

@Composable
fun CartScreen(productId: String, viewModel: ProductViewModel) {
    val product = remember(productId) {
        viewModel.getProductById(productId.toIntOrNull() ?: -1)
    }

    if (product == null) {
        Text("Aucun produit dans le panier", modifier = Modifier.padding(16.dp))
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("🛒 Produit dans le panier :")
            Spacer(modifier = Modifier.height(8.dp))
            Text("📦 ${product.title}")
            Text("💰 ${product.price} $")
        }
    }
}
