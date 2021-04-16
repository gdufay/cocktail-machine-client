package com.example.cocktailmachine.ui.cocktaillist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.cocktailmachine.data.CocktailRepository
import com.example.cocktailmachine.data.IngredientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CocktailListViewModel @Inject constructor(
    cocktailRepository: CocktailRepository,
    ingredientRepository: IngredientRepository,
) :
    ViewModel() {

    private val searchQuery = MutableStateFlow("")
    private val filter = MutableStateFlow(mutableListOf<Int>())

    private val cocktailsFlow = combine(searchQuery, filter) { query, filter ->
        Pair(query, filter)
    }.flatMapLatest { (query, filters) ->
        if (filters.isEmpty())
            cocktailRepository.getCocktails(query)
        else
            cocktailRepository.getCocktails(query, filters)
    }

    val cocktails = cocktailsFlow.asLiveData()

    val ingredients = ingredientRepository.getIngredients().map {
        it.map { (name, id) -> IngredientFilter(id, name, false) }
    }.asLiveData()

    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun editFilter(ingredient: Int, isChecked: Boolean) {
        val list = mutableListOf(filter.value).flatten().toMutableList()

        if (isChecked)
            list.add(ingredient)
        else
            list.remove(ingredient)
        filter.value = list
    }
}

data class IngredientFilter(
    val ingredientId: Int,
    val ingredientName: String,
    var isChecked: Boolean,
)