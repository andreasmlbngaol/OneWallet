package com.mightysana.onewallet.main.presentation.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mightysana.onewallet.Home
import com.mightysana.onewallet.oneproject.auth.model.SignIn
import com.mightysana.onewallet.oneproject.components.OneScreen
import com.mightysana.onewallet.oneproject.model.OneProject
import com.mightysana.onewallet.oneproject.model.convertMillisToDate
import com.mightysana.onewallet.oneproject.model.convertMillisToDateTime
import com.mightysana.onewallet.oneproject.model.navigateAndPopUp


@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    OneScreen(viewModel.appState.collectAsState().value) {
        val userProfile by viewModel.userProfile.collectAsState()
        val profile = listOf(
            "UID: ${userProfile.uid}",
            "Name: ${userProfile.name}",
            "Email: ${userProfile.email}",
            "Profile Photo URL: ${userProfile.profilePhotoUrl}",
            "Phone Number: ${userProfile.phoneNumber}",
            "Bio: ${userProfile.bio}",
            "Birth Date: ${userProfile.birthDate?.convertMillisToDate()}",
            "Gender: ${userProfile.gender}",
            "Address: ${userProfile.address}",
            "Created At: ${userProfile.createdAt.convertMillisToDateTime()}",
            "Last Sign In: ${userProfile.lastLoginAt.convertMillisToDateTime()}",
            "Verified: ${userProfile.verified}",
            "Is Wallet User: ${viewModel.isWalletUser.collectAsState().value}"
        )
        Scaffold { innerPadding ->
            LazyColumn(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(innerPadding).padding(horizontal = OneProject.HorizontalPadding).fillMaxWidth()
            ) {
                item {
                    OutlinedButton(
                        onClick = {
                            viewModel.onSignOut {
                                navController.navigateAndPopUp(SignIn, Home)
                            }
                        }
                    ) {
                        Text(text = "Sign Out From Home")
                    }
                }

                items(profile) {
                    Text(text = it)
                }
            }
        }
    }
}