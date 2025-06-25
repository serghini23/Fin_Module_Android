package com.example.emtyapp.ui.product

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emtyapp.data.Entities.Order
import com.example.emtyapp.data.Entities.Product
import com.example.emtyapp.data.Repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProductViewState())
    val state: StateFlow<ProductViewState> = _state

    private val _cartItems = MutableStateFlow<List<Product>>(emptyList())
    val cartItems: StateFlow<List<Product>> = _cartItems

    fun addToCart(product: Product) {
        _cartItems.value = _cartItems.value + product
    }

    fun removeFromCart(productId: Int) {
        _cartItems.value = _cartItems.value.filterNot { it.id == productId }
    }

    fun handleIntent(intent: ProductIntent) {
        when (intent) {
            is ProductIntent.LoadProducts -> {
                viewModelScope.launch {
                    loadProducts()
                }
            }
        }
    }
    private fun clearCart() {
        _cartItems.value = emptyList()
    }


    fun getProductById(id: Int): Product? {
        return _state.value.products.find { it.id == id }
    }

    private suspend fun loadProducts() {
        _state.value = _state.value.copy(isLoading = true, error = null)
        try {
            val products = repository.getProducts()
            _state.value = ProductViewState(isLoading = false, products = products)
        } catch (e: Exception) {
            _state.value = ProductViewState(
                isLoading = false,
                error = e.message ?: "Error fetching products"
            )
        }
    }
    private var orderCounter = 0
    private val _orderHistory = mutableStateListOf<Order>()
    val orderHistory: List<Order> = _orderHistory

    fun placeOrder() {
        val currentCart = cartItems.value
        if (currentCart.isNotEmpty()) {
            _orderHistory.add(
                Order(
                    id = orderCounter++,
                    items = currentCart.toList(),
                    timestamp = System.currentTimeMillis()
                )
            )
            clearCart()
        }
    }
}

