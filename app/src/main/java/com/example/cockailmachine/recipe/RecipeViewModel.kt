package com.example.cockailmachine.recipe

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cockailmachine.database.CocktailDatabase
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val database: CocktailDatabase,
    private val application: Application
) : ViewModel() {

    private val _recipes = MutableLiveData<String>()

    val recipes: LiveData<String>
        get() = _recipes

    init {
        initializeRecipes()
    }

    private fun initializeRecipes() {
        viewModelScope.launch {
            _recipes.value = getRecipeFromDatabase()
        }
    }

    private suspend fun getRecipeFromDatabase(): String {
        return database.cocktailDao().get(0)?.name ?: "Nop"
    }
}