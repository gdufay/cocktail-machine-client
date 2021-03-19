package com.example.cockailmachine.recipe

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.cockailmachine.database.CocktailDatabase

class RecipeViewModel(
    database: CocktailDatabase,
    private val application: Application
) : ViewModel() {

    // private val _recipes = MutableLiveData<List<Cocktail>>()

    val recipes = database.cocktailDao().getAll()

    /*
    init {
        initializeRecipes()
    }

    private fun initializeRecipes() {
        viewModelScope.launch {
            _recipes.value = getRecipeFromDatabase()
        }
    }

    private suspend fun getRecipeFromDatabase(): LiveData<List<Cocktail>> {
        return database.cocktailDao().getAll()
    }
     */
}