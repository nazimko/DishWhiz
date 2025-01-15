package com.mhmtn.DishWhiz.category.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmtn.DishWhiz.category.domain.CategoryDataSource
import com.mhmtn.DishWhiz.core.domain.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val dataSource: CategoryDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(CategoriesState())
    val state = _state
        .onStart { getCategories() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            CategoriesState()
        )

    private fun getCategories() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            val result = dataSource.getCategories()
            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            categories = result.data!!.categories,
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
}