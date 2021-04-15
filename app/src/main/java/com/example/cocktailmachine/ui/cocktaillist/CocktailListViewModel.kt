package com.example.cocktailmachine.ui.cocktaillist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.cocktailmachine.data.CocktailRepository
import com.example.cocktailmachine.data.IngredientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class CocktailListViewModel @Inject constructor(
    cocktailRepository: CocktailRepository,
    ingredientRepository: IngredientRepository,
) :
    ViewModel() {

    val searchQuery = MutableStateFlow("")

    private val cocktailsFlow = searchQuery.flatMapLatest {
        cocktailRepository.getCocktails(it)
    }

    val cocktails = cocktailsFlow.asLiveData()

    val ingredients = ingredientRepository.getIngredients().asLiveData()
}