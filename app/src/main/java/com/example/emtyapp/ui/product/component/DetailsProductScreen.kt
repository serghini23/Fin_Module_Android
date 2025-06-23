package com.example.emtyapp.ui.product.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.emtyapp.ui.product.ProductViewModel
import androidx.compose.material3.Button
import androidx.compose.material3.Text


@Composable
fun DetailsScreen(
    productId: String,
    viewModel: ProductViewModel,
    onAddToCart: () -> Unit
) {
    val product = remember(productId) {
        viewModel.getProductById(productId.toIntOrNull() ?: -1)
    }

    if (product == null) {
        Text("Produit introuvable", modifier = Modifier.padding(16.dp))
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = "📦 ${product.title}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "💬 ${product.description}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "💰 ${product.price} $")
            Text(text = "📂 Catégorie: ${product.category}")
            Text(text = "⭐ Note: ${product.rating.rate} (${product.rating.count} avis)")

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onAddToCart) {
                Text("Ajouter au Panier 🛒")
            }
        }
    }
}

