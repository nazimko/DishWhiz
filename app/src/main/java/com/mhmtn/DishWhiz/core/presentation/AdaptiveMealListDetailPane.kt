@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.mhmtn.DishWhiz.core.presentation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mhmtn.DishWhiz.meal.meal.presentation.MealViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalUriHandler
import com.mhmtn.DishWhiz.meal.meal.presentation.MealListScreen
import com.mhmtn.DishWhiz.meal_detail.presentation.DetailScreen
import com.mhmtn.DishWhiz.meal_detail.presentation.MealDetailViewModel
import kotlinx.coroutines.launch

@Composable
fun AdaptiveMealListDetailPane(
    modifier: Modifier = Modifier,
    viewModel: MealViewModel = koinViewModel(),
    viewModel2 : MealDetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val state2 by viewModel2.state.collectAsStateWithLifecycle()
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    val coroutineScope = rememberCoroutineScope()
    val uriHandler = LocalUriHandler.current

    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                MealListScreen(
                    state = state,
                    onNavigate = {
                        coroutineScope.launch{
                            navigator.navigateTo(
                                pane = ListDetailPaneScaffoldRole.List
                            )
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                DetailScreen(
                    state = state2,
                    onWatchYoutube = {
                        viewModel2.onYoutubeClick(uriHandler,it)
                    }
                )
            }
        },
        modifier = modifier
    )
}