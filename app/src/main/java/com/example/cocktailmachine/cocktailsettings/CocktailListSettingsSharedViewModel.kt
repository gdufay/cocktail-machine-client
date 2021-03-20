package com.example.cocktailmachine.cocktailsettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cocktailmachine.database.CocktailWithIngredients

class CocktailListSettingsSharedViewModel : ViewModel() {
    private val _selected = MutableLiveData<CocktailWithIngredients>()

    val selected: LiveData<CocktailWithIngredients>
        get() = _selected

    fun select(cocktail: CocktailWithIngredients) {
        _selected.value = cocktail
    }
}