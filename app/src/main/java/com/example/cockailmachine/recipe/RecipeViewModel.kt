package com.example.cockailmachine.recipe

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.cockailmachine.database.CocktailDatabase

class RecipeViewModel(
    database: CocktailDatabase,
    private val application: Application
) : ViewModel() {

    val cocktails = database.cocktailDao().getCocktailsWithIngredients()

}