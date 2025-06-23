package com.example.emtyapp.ui.product.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
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

    val isAddedToCart = remember { mutableStateOf(false) }

    if (product == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("❌ Produit introuvable", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(24.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Image
                Image(
                    painter = rememberAsyncImagePainter(product.image),
                    contentDescription = product.title,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    contentScale = ContentScale.Crop
                )

                // Title
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )

                // Description
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 5
                )

                // Price & info
                Text(
                    text = "💰 ${product.price} $",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text("📂 Catégorie : ${product.category}")
                Text("⭐ Note : ${product.rating.rate} (${product.rating.count} avis)")

                Spacer(modifier = Modifier.height(16.dp))

                // Buttons
                if (!isAddedToCart.value) {
                    Button(
                        onClick = {
                            onAddToCart()
                            isAddedToCart.value = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Ajouter au Panier 🛒")
                    }
                } else {
                    Text(
                        "✅ Ajouté au panier",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
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
