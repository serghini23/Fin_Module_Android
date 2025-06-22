package com.example.emtyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.emtyapp.nav.AppNavigation
import com.example.emtyapp.ui.product.ProductViewModel
import com.example.emtyapp.ui.theme.EmtyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EmtyAppTheme {
                Surface{

                 val viewModel:ProductViewModel = hiltViewModel()

                    AppNavigation(viewModel)
                }
            }
        }
    }
}



