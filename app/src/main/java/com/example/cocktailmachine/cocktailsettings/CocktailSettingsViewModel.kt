package com.example.cocktailmachine.cocktailsettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cocktailmachine.database.CocktailWithIngredients

class CocktailSettingsViewModel : ViewModel() {
    private val _cocktail = MutableLiveData<CocktailWithIngredients>()

    val cocktail: LiveData<CocktailWithIngredients>
        get() = _cocktail

    fun setCocktail(cocktail: CocktailWithIngredients) {
        _cocktail.value = cocktail
    }
}