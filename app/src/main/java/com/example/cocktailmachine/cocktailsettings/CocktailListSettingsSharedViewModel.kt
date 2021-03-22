package com.example.cocktailmachine.cocktailsettings

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cocktailmachine.database.CocktailDatabase
import com.example.cocktailmachine.database.CocktailWithIngredients

class CocktailListSettingsSharedViewModel(database: CocktailDatabase) : ViewModel() {
    private val _cocktails = database.cocktailDao().getCocktailsWithIngredients()
    val cocktails: LiveData<List<CocktailWithIngredients>>
        get() = _cocktails

    private val _selected_cocktail = MutableLiveData<CocktailWithIngredients>()
    val selected_cocktail: LiveData<CocktailWithIngredients>
        get() = _selected_cocktail

    fun selectCocktail(cocktail: CocktailWithIngredients) {
        _selected_cocktail.value = cocktail
    }

    fun onClickAddCocktail() {
        Log.i("ViewModel", "click")
    }
}