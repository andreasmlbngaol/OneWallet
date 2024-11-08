package com.mightysana.onewallet.main.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mightysana.onewallet.Main
import com.mightysana.onewallet.AppViewModel
import com.mightysana.onewallet.R
import com.mightysana.onewallet.main.presentation.dashboard.DashboardScreen
import com.mightysana.onewallet.oneproject.auth.model.AuthGraph
import com.mightysana.onewallet.oneproject.auth.model.SignIn
import com.mightysana.onewallet.oneproject.components.OneIcon
import com.mightysana.onewallet.oneproject.model.BottomNavBarItem
import com.mightysana.onewallet.oneproject.model.OneIcons
import com.mightysana.onewallet.oneproject.model.OneProject
import com.mightysana.onewallet.oneproject.model.navigateAndPopUp
import kotlinx.serialization.Serializable

@Serializable
object Dashboard

@Serializable
object Transactions

@Serializable
object Wallets

@Serializable
object Debts

@Serializable
object Profile

class Bottom

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    appController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val mainController = rememberNavController()
    val bottomNavDestinations: List<BottomNavBarItem> = listOf(
        BottomNavBarItem(
            labelResId = R.string.dashboard,
            selectedIcon = OneIcons.DashboardSelected,
            unselectedIcon = OneIcons.DashboardUnselected,
            route = Dashboard
        ),
        BottomNavBarItem(
            labelResId = R.string.transactions,
            selectedIcon = OneIcons.TransactionsSelected,
            unselectedIcon = OneIcons.TransactionsUnselected,
            route = Transactions
        ),
        BottomNavBarItem(
            labelResId = R.string.wallets,
            selectedIcon = OneIcons.WalletsSelected,
            unselectedIcon = OneIcons.WalletsUnselected,
            route = Wallets
        ),
        BottomNavBarItem(
            labelResId = R.string.debts,
            selectedIcon = OneIcons.DebtsSelected,
            unselectedIcon = OneIcons.DebtsUnselected,
            route = Debts
        ),
        BottomNavBarItem(
            labelResId = R.string.profile,
            selectedIcon = OneIcons.ProfileSelected,
            unselectedIcon = OneIcons.ProfileUnselected,
            route = Profile
        )
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.onSignOut {
                                appController.navigateAndPopUp(AuthGraph, Main)
                            }
                        }
                    ) {
                        OneIcon(OneIcons.Logout)
                    }

                },
                scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
            )
        },
        bottomBar = {
            NavigationBar {
                val entry by mainController.currentBackStackEntryAsState()
                val currentDestination = entry?.destination
                bottomNavDestinations.forEachIndexed { index, item ->
                    val selected = currentDestination?.hierarchy?.any {
                        it.route != item.route
                    }
                    if (selected != null) {
                        NavigationBarItem(
                            selected = selected,
                            icon = {
                                OneIcon(if(selected) item.selectedIcon else item.unselectedIcon)
                            },
                            label = {
                                Text(
                                    text = stringResource(item.labelResId),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            onClick = {
                                if(!selected)
                                    mainController.navigate(item.route)
                            }
                        )
                    }
                }
            }
        }
    ) {
        Surface(
            modifier = Modifier.padding(it).padding(horizontal = OneProject.HorizontalPadding)
        ) {
            NavHost(
                navController = mainController,
                startDestination = Dashboard,
                modifier = Modifier
            ) {
                composable<Dashboard> {
                    DashboardScreen(
                        navController = mainController
                    )
                }
                composable<Transactions> {
                    Text(text = "Transactions")
                }
                composable<Wallets> {}
                composable<Debts> {}
                composable<Profile> {}
            }
        }
    }
}