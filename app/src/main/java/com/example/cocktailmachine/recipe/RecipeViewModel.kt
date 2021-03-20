package com.example.cocktailmachine.recipe

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.cocktailmachine.database.CocktailDatabase

class RecipeViewModel(
    database: CocktailDatabase,
    private val application: Application
) : ViewModel() {

    val cocktails = database.cocktailDao().getCocktailsWithIngredients()

}