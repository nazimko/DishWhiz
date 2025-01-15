package com.mhmtn.DishWhiz.meal_detail.presentation

import androidx.compose.ui.platform.UriHandler
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmtn.DishWhiz.core.domain.util.Constants.MEAL_ID
import com.mhmtn.DishWhiz.core.domain.util.Resource
import com.mhmtn.DishWhiz.meal_detail.domain.MealDetailDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MealDetailViewModel(
    private val dataSource: MealDetailDataSource,
    private val stateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(MealDetailState())
    val state = _state
        .onStart {
            stateHandle.get<String>(MEAL_ID)?.let {
                getMealDetail(it)
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            MealDetailState()
        )

    private fun getMealDetail(mealId: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            val result = dataSource.getMealDetail(mealId)
            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            mealDetail = result.data!!.meals[0],
                            isLoading = false
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
     fun onYoutubeClick(uriHandler: UriHandler, url: String){
        uriHandler.openUri(url)
    }
}
