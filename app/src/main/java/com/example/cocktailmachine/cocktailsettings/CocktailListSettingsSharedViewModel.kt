package com.example.cocktailmachine.cocktailsettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cocktailmachine.database.CocktailDatabase
import com.example.cocktailmachine.database.CocktailWithIngredients
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CocktailListSettingsSharedViewModel @Inject constructor(val database: CocktailDatabase) :
    ViewModel() {

    private val _cocktails = database.cocktailDao().getCocktailsWithIngredients()
    val cocktails: LiveData<List<CocktailWithIngredients>>
        get() = _cocktails

    private val _selectedCocktail = MutableLiveData<CocktailWithIngredients>()
    val selectedCocktail: LiveData<CocktailWithIngredients>
        get() = _selectedCocktail

    fun selectCocktail(cocktail: CocktailWithIngredients) {
        _selectedCocktail.value = cocktail
    }
}