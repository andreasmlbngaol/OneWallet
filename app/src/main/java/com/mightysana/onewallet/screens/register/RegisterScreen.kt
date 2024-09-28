package com.mightysana.onewallet.screens.register

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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Register Wallets", style = MaterialTheme.typography.headlineMedium)

            // Input for wallet name
            TextField(
                value = walletName.value,
                onValueChange = { walletName.value = it },
                label = { Text("Wallet Name") },
                modifier = Modifier.fillMaxWidth()
            )

            // Input for wallet balance
            TextField(
                value = walletBalance.value,
                onValueChange = { walletBalance.value = it },
                label = { Text("Initial Balance") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Wallet")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // List of wallets added
            Text(text = "Your Wallets", style = MaterialTheme.typography.headlineSmall)

            LazyColumn {
                items(wallets.value) { wallet ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(wallet.name)
                        Text("Balance: ${wallet.balance}")
                    }
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Wallets")
            }
        }
    }
}