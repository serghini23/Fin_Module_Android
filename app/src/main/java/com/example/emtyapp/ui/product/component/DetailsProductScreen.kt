package com.example.emtyapp.ui.product.component
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.emtyapp.ui.product.ProductViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow



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
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (isPressed) 1.03f else 1f)
    val isAddedToCart = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    if (product == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("❌ Produit introuvable", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            shadowElevation = 12.dp.toPx()
                            shape = RoundedCornerShape(24.dp)
                            clip = true
                        }
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    isPressed = true
                                    tryAwaitRelease()
                                    isPressed = false
                                }
                            )
                        },
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                ) {
                    Box {
                        Image(
                            painter = rememberAsyncImagePainter(product.image),
                            contentDescription = product.title,
                            modifier = Modifier
                                .height(280.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.5f)),
                                        startY = 0f,
                                        endY = 280f
                                    )
                                )
                        )
                        Text(
                            text = product.title,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp),
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                shadow = Shadow(
                                    color = Color.Black,
                                    offset = Offset(2f, 2f),
                                    blurRadius = 6f
                                )
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    maxLines = 6,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(16.dp))

                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), thickness = 1.dp)

                Spacer(Modifier.height(16.dp))

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "💰 Prix : ${product.price} $",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "📂 Catégorie : ${product.category}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(
                            text = "⭐ Note : ${product.rating.rate} (${product.rating.count} avis)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                AnimatedVisibility(
                    visible = !isAddedToCart.value,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Button(
                        onClick = {
                            onAddToCart()
                            isAddedToCart.value = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            "Ajouter au Panier 🛒",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }

                AnimatedVisibility(
                    visible = isAddedToCart.value,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "✅ Ajouté au panier",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.ExtraBold,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            OutlinedButton(
                                onClick = onNavigateHome,
                                shape = RoundedCornerShape(24.dp),
                                modifier = Modifier.weight(1f).padding(end = 8.dp)
                            ) {
                                Icon(Icons.Default.Home, contentDescription = "Accueil")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Accueil")
                            }
                            Button(
                                onClick = onNavigateToCart,
                                shape = RoundedCornerShape(24.dp),
                                modifier = Modifier.weight(1f).padding(start = 8.dp)
                            ) {
                                Icon(Icons.Default.ShoppingCart, contentDescription = "Panier")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Voir Panier")
                            }
                        }
                    }
                }
            }
        }
    }
}
