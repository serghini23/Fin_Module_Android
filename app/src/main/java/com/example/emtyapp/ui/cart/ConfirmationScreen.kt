package com.example.emtyapp.ui.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.Icon

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Lock





@Composable
fun ConfirmationScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    var isOrderConfirmed by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    // Validation checks
    val isNameValid = name.isNotBlank()
    val isEmailValid = email.contains("@") && email.contains(".")
    val isAddressValid = address.isNotBlank()
    val isPhoneValid = phone.length >= 8 && phone.all { it.isDigit() }
    val isCardNumberValid = cardNumber.length == 16 && cardNumber.all { it.isDigit() }
    val isExpiryValid = expiryDate.matches(Regex("""^(0[1-9]|1[0-2])\/\d{2}$"""))
    val isCvvValid = cvv.length == 3 && cvv.all { it.isDigit() }

    val isFormValid =
        isNameValid && isEmailValid && isAddressValid &&
                isPhoneValid && isCardNumberValid && isExpiryValid && isCvvValid

    Scaffold { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            if (!isOrderConfirmed) {
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Finaliser votre commande",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Merci de remplir les champs ci-dessous pour confirmer votre achat.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 24.dp),
                        textAlign = TextAlign.Center
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Informations personnelles", style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("Nom complet") },
                                leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Nom") },
                                isError = !isNameValid,
                                modifier = Modifier.fillMaxWidth()
                            )
                            if (!isNameValid) {
                                Text("Le nom est requis", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                label = { Text("Email") },
                                leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") },
                                isError = !isEmailValid,
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                            )
                            if (!isEmailValid) {
                                Text("Email invalide", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = address,
                                onValueChange = { address = it },
                                label = { Text("Adresse de livraison") },
                                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = "Adresse") },
                                isError = !isAddressValid,
                                modifier = Modifier.fillMaxWidth()
                            )
                            if (!isAddressValid) {
                                Text("Adresse requise", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = phone,
                                onValueChange = { phone = it },
                                label = { Text("Téléphone") },
                                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = "Téléphone") },
                                isError = !isPhoneValid,
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                            )
                            if (!isPhoneValid) {
                                Text("Téléphone invalide", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text("Paiement", style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = cardNumber,
                                onValueChange = { cardNumber = it },
                                label = { Text("Numéro de carte (16 chiffres)") },
                                leadingIcon = { Icon(Icons.Default.CreditCard, contentDescription = "Carte") },
                                isError = !isCardNumberValid,
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            if (!isCardNumberValid) {
                                Text("Numéro de carte invalide", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(modifier = Modifier.fillMaxWidth()) {
                                OutlinedTextField(
                                    value = expiryDate,
                                    onValueChange = { expiryDate = it },
                                    label = { Text("MM/YY") },
                                    leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = "Date expiration") },
                                    isError = !isExpiryValid,
                                    modifier = Modifier.weight(1f).padding(end = 4.dp)
                                )
                                OutlinedTextField(
                                    value = cvv,
                                    onValueChange = { cvv = it },
                                    label = { Text("CVV") },
                                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "CVV") },
                                    isError = !isCvvValid,
                                    modifier = Modifier.weight(1f).padding(start = 4.dp),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                )
                            }
                            if (!isExpiryValid || !isCvvValid) {
                                val errorText = buildString {
                                    if (!isExpiryValid) append("Date expiration invalide")
                                    if (!isExpiryValid && !isCvvValid) append(" et ")
                                    if (!isCvvValid) append("CVV invalide")
                                }
                                Text(errorText, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { isOrderConfirmed = true },
                        enabled = isFormValid,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Text("✅ Confirmer la commande")
                    }
                }
            } else {
                // Success screen with simple fade in (could be animated)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "✅ Merci pour votre commande!",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Votre commande a été confirmée et sera traitée rapidement.",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = {
                            navController.navigate("home") {
                                popUpTo("home") { inclusive = true }
                            }
                        }
                    ) {
                        Text("Retour à l'accueil")
                    }
                }
            }
        }
    }
}
