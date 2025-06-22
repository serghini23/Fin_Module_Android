package com.example.emtyapp.ui.product.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.emtyapp.data.Entities.Product

@Composable
fun ProductItem(product: Product, onNavigateToDetails: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "📦 ${product.title}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "💬 ${product.description}", maxLines = 2)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "💰 ${product.price} $")
            Text(text = "📂 Category: ${product.category}")
            Text(text = "⭐ ${product.rating.rate} (${product.rating.count} avis)")
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { onNavigateToDetails(product.id.toString()) }) {
                Text(text = "Plus de détails...")
            }
        }
    }
}
