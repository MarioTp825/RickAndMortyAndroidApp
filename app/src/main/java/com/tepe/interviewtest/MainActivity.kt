package com.tepe.interviewtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.tepe.interviewtest.character.view.dashboard.CharacterDashboardScreen
import com.tepe.interviewtest.character.view.detail.CharacterDetailScreen
import com.tepe.interviewtest.ui.theme.InterviewTestTheme
import com.tepe.interviewtest.utils.NavLinks
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val controller = rememberNavController()
            InterviewTestTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    NavHost(
                        navController = controller,
                        startDestination = NavLinks.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        navigation(
                            route = NavLinks.Home.route,
                            startDestination = NavLinks.HomeCharacterList.route
                        ) {
                            composable(NavLinks.HomeCharacterList.route) {

                                CharacterDashboardScreen { id ->
                                    val route = NavLinks.HomeCharacterDetail.route
                                        .replace("{id}", id.toString())
                                    controller.navigate(route)
                                }
                            }
                            composable(
                                route = NavLinks.HomeCharacterDetail.route,
                                arguments = listOf(
                                    navArgument(name = "id") {
                                        type = NavType.IntType
                                    }
                                )
                            ) { backStackEntry ->
                                val id = backStackEntry.arguments?.getInt("id") ?: -1
                                CharacterDetailScreen(
                                    id = id,
                                    navigateBack = { controller.popBackStack() }
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}

