package com.avito.tech.intern.presentation.ui.home


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.avito.tech.intern.presentation.nav.Screen
import com.avito.tech.intern.presentation.nav.TrackDetails
import com.avito.tech.intern.presentation.ui.local.LocalScreen
import com.avito.tech.intern.presentation.ui.player.PlayerScreen
import com.avito.tech.intern.presentation.ui.remote.RemoteScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(

) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val screens = listOf(Screen.Search, Screen.Dowloaded)
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    val bottomBarDestination = screens.any { it.route == currentDestination?.route }


    Scaffold(
        topBar = {
            if (bottomBarDestination) {
                TopAppBar(title = {
                    Text(screens[selectedItemIndex].title)
                })
            }
        },
        bottomBar = {
            if (bottomBarDestination) {
                NavigationBar {
                    screens.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                                navController.navigate(item.route)
                            },
                            label = { Text(text = item.title) },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = "Navigation icon"
                                )
                            }
                        )
                    }
                }

            }

        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = Screen.Search.route
        ) {
            composable(Screen.Search.route) {
                RemoteScreen { trackDetails ->
                    navController.navigate(
                        trackDetails
                    )
                }
            }
            composable(Screen.Dowloaded.route) {
                LocalScreen { trackDetails ->
                    navController.navigate(
                        trackDetails
                    )
                }
            }
            composable<TrackDetails> { navBackStack ->
                val track = navBackStack.toRoute<TrackDetails>()
                PlayerScreen(trackDetails = track)
            }
        }

    }
}