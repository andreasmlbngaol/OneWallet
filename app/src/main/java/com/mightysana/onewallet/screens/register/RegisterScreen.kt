package com.mightysana.onewallet.screens.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mightysana.onewallet.screens.login.navigateAndPopUp

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val walletName = remember { mutableStateOf("") }
    val walletBalance = remember { mutableStateOf("") }
    val wallets = viewModel.wallets.collectAsState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Sign Out Button
//            Button(
//                onClick = {
//                    viewModel.signOut()
//                    navController.navigateAndPopUp("login", "home")
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(text = "Sign Out")
//            }

            // Header
            Text(text = "Register Wallets", style = MaterialTheme.typography.headlineMedium)

            // Input for wallet name
            OutlinedTextField(
                shape = MaterialTheme.shapes.medium,
                singleLine = true,
                value = walletName.value.trim(),
                onValueChange = { walletName.value = it },
                label = { Text("Wallet Name") },
                modifier = Modifier.fillMaxWidth()
            )

            // Input for wallet balance
            OutlinedTextField(
                shape = MaterialTheme.shapes.medium,
                singleLine = true,
                value = walletBalance.value,
                onValueChange = { walletBalance.value = it },
                label = { Text("Initial Balance") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Text("Rp.")
                }
            )

            // Add wallet button
            Button(
                onClick = {
                    if (walletName.value.isNotEmpty() && walletBalance.value.isNotEmpty()) {
                        viewModel.addWallet(walletName.value, walletBalance.value.toInt())
                        walletName.value = ""
                        walletBalance.value = ""
                    }
                },
                enabled = walletName.value.isNotBlank() && walletBalance.value.isNotBlank()
            ) {
                Text("Add Wallet")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // List of wallets added
            Text(text = "Your Wallets", style = MaterialTheme.typography.headlineSmall)

            LazyColumn(
                modifier = Modifier.height(300.dp)
            ) {
                items(wallets.value) { wallet ->
                    Card(
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                text = wallet.name,
                                modifier = Modifier
                                    .weight(0.65f),
                                textAlign = TextAlign.Start
                            )
                            Text(
                                text = "Rp.",
                                modifier = Modifier
                                    .weight(0.1f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = wallet.balance.moneyFormat(),
                                modifier = Modifier
                                    .weight(0.25f),
                                textAlign = TextAlign.End
                            )
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Wallet",
                                modifier = Modifier
                                    .weight(0.1f)
                                    .clickable {
                                        viewModel.deleteWallet(wallet)

                                    }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Save button
            Button(
                onClick = {
                    viewModel.saveWalletsToDatabase {
                        navController.navigateAndPopUp("home", "register")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = wallets.value.isNotEmpty()
            ) {
                Text("Save Wallets")
            }

        }
    }
}

fun Int.moneyFormat(): String {
    return this.toString().reversed().chunked(3).joinToString(".").reversed()
}