package com.example.cocktailmachine.cocktaillist

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.cocktailmachine.database.CocktailDatabase

class CocktailListViewModel(
    database: CocktailDatabase,
    private val application: Application
) : ViewModel() {

    val cocktails = database.cocktailDao().getCocktailsWithIngredients()

}