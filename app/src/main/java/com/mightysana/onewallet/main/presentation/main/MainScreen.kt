package com.mightysana.onewallet.main.presentation.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mightysana.onewallet.Main
import com.mightysana.onewallet.R
import com.mightysana.onewallet.main.presentation.dashboard.DashboardScreen
import com.mightysana.onewallet.oneproject.auth.model.AuthGraph
import com.mightysana.onewallet.oneproject.components.OneAsyncImage
import com.mightysana.onewallet.oneproject.components.OneIcon
import com.mightysana.onewallet.oneproject.model.BottomNavBarItem
import com.mightysana.onewallet.oneproject.model.OneIcons
import com.mightysana.onewallet.oneproject.model.OneProject
import com.mightysana.onewallet.oneproject.model.getObject
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
    )

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val userProfile by viewModel.userProfile.collectAsState()
    val expanded by viewModel.expanded.collectAsState()

    val containerColor = MaterialTheme.colorScheme.secondaryContainer
    val contentColor = contentColorFor(containerColor)
    val selectedContainerColor = MaterialTheme.colorScheme.secondary
    val selectedContentColor = contentColorFor(selectedContainerColor)

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = containerColor,
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = contentColor,
                ),
                title = {
//                    val nickname = userProfile.nickname
                    val nickname = userProfile.name
                    Text(
                        text = viewModel.greetings(
                            morning = stringResource(R.string.good_morning, nickname),
                            day = stringResource(R.string.good_day, nickname),
                            afternoon = stringResource(R.string.good_afternoon, nickname),
                            evening = stringResource(R.string.good_evening, nickname)
                        ),
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.onSignOut {
                                appController.navigateAndPopUp(AuthGraph, Main)
                            }
                        },
                        modifier = Modifier
                            .border(
                                BorderStroke(
                                    width = 2.dp,
                                    color = contentColor
                                ),
                                shape = CircleShape
                            )
                    ) {
                        OneAsyncImage(
                            model = userProfile.profilePhotoUrl,
                            modifier = Modifier
                                .clip(CircleShape)
                        )
                    }

                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = containerColor,
                contentColor = contentColor,
                modifier = Modifier.clip(MaterialTheme.shapes.medium),
                windowInsets = NavigationBarDefaults.windowInsets
            ) {
                val entry by mainController.currentBackStackEntryAsState()
                val currentDestination = entry?.destination
                bottomNavDestinations.forEach { navItem ->
                    val selected = currentDestination?.route?.getObject() == navItem.route
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors().copy(
                            selectedIndicatorColor = selectedContainerColor,
                            selectedIconColor = selectedContentColor,
                            unselectedIconColor = contentColor,
                            unselectedTextColor = contentColor
                        ),
                        selected = selected,
                        icon = {
                            OneIcon(
                                imageVector = if (selected) navItem.selectedIcon else navItem.unselectedIcon,
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(navItem.labelResId),
                                style = MaterialTheme.typography.labelSmall,
                            )
                        },
                        onClick = {
                            if (!selected) {
                                val currentRoute = currentDestination?.route!!.getObject()!!
                                mainController.navigateAndPopUp(navItem.route, currentRoute)
                            }
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            AnimatedContent(expanded, label = "") {
                val buttonContainerColor = MaterialTheme.colorScheme.primaryContainer
                val buttonContentColor = contentColorFor(buttonContainerColor)

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val optionContainerColor = MaterialTheme.colorScheme.secondary
                    val optionContentColor = contentColorFor(optionContainerColor)

                    if (it) {
                        ExtendedFloatingActionButton(
                            onClick = {},
                            text = { Text(text = "Wallet") },
                            containerColor = buttonContainerColor,
                            contentColor = buttonContentColor,
                            icon = { OneIcon(OneIcons.WalletsUnselected) }
                        )

                        ExtendedFloatingActionButton(
                            onClick = {},
                            text = { Text(text = "Debt") },
                            containerColor = buttonContainerColor,
                            contentColor = buttonContentColor,
                            icon = { OneIcon(OneIcons.DebtsUnselected) }
                        )

                        ExtendedFloatingActionButton(
                            onClick = {},
                            text = { Text(text = "Transaction") },
                            containerColor = buttonContainerColor,
                            contentColor = buttonContentColor,
                            icon = { OneIcon(OneIcons.TransactionsUnselected) }
                        )

                    }

                    ExtendedFloatingActionButton(
                        expanded = !it,
                        onClick = { viewModel.toggleExpanded() },
                        text = { Text(text = "New") },
                        containerColor = optionContainerColor,
                        contentColor = optionContentColor,
                        icon = { OneIcon(if(!it) OneIcons.Plus else OneIcons.Minus) }
                    )
                }
            }
        }

    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = OneProject.HorizontalPadding)
                .padding(top = OneProject.TopPadding)
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
                composable<Wallets> {
                    Text(text = "Wallets")
                }
                composable<Debts> {
                    Text(text = "Debts")
                }
                composable<Profile> {
                    Text(text = "Profile")
                }
            }
        }
    }
}