package com.mightysana.onewallet.main.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.mightysana.onewallet.Home
import com.mightysana.onewallet.auth.SignIn
import com.mightysana.onewallet.navigateAndPopUp

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    Scaffold { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(innerPadding)
        ) {
            OutlinedButton(
                onClick = {
                    Firebase.auth.signOut()
                    navController.navigateAndPopUp(SignIn, Home)
                }
            ) {
                Text(text = "Sign Out")
            }
        }
    }
}
