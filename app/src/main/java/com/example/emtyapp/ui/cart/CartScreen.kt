package com.example.emtyapp.ui.cart

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.example.emtyapp.ui.product.ProductViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun CartScreen(productId: String, viewModel: ProductViewModel) {
    val product = remember(productId) {
        viewModel.getProductById(productId.toIntOrNull() ?: -1)
    }

    if (product == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("🛒 Le panier est vide", style = MaterialTheme.typography.bodyLarge)
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
                Text("🛒 Panier", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))
                Text("📦 ${product.title}", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("💰 ${product.price} $", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

