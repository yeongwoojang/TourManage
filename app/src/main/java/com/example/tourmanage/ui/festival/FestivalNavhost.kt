package com.example.tourmanage.ui.festival

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.data.FestivalNavItem
import com.example.tourmanage.viewmodel.FestivalArgument
import com.example.tourmanage.viewmodel.FestivalViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun FestivalNavHost(navController: NavHostController, mainFestival: ArrayList<FestivalItem>) {
    val viewModel: FestivalViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = FestivalNavItem.Main.route
    ) {
        composable(FestivalNavItem.Main.route) {
            FestivalMainWidget(
                viewModel = viewModel,
                mainFestival = mainFestival,
                choiceFestival = {
                    navController.navigate("${FestivalNavItem.Detail.route}/$it")
                }
            )
        }

        composable(
            route = "${FestivalNavItem.Detail.route}/{contentId}",
            arguments = listOf(
                navArgument("contentId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val contentId = backStackEntry.arguments?.getString("contentId")
            LaunchedEffect(Unit) {
                viewModel.requestFestivalDetail(contentId)
            }
            FestivalDetailScreen(
                viewModel = viewModel,
                festivalDetailState = viewModel.festivalDetailInfo.collectAsStateWithLifecycle()
            )
        }
    }
}