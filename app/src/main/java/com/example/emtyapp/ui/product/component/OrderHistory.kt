package com.example.emtyapp.ui.product.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.emtyapp.ui.product.ProductViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp



@Composable
fun OrderHistoryScreen(viewModel: ProductViewModel) {
    val orders = viewModel.orderHistory

    if (orders.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("📭 Aucun historique de commande.")
        }
    } else {
        Column(Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
            Text("📜 Historique des commandes", style = MaterialTheme.typography.headlineSmall)

            orders.forEach { order ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("🆔 Commande #${order.id+1}")
                        Text("🕒 ${java.text.SimpleDateFormat("dd MMM yyyy, HH:mm").format(order.timestamp)}")
                        Spacer(Modifier.height(8.dp))
                        order.items.forEach {
                            Text("• ${it.title} - ${it.price} $", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
