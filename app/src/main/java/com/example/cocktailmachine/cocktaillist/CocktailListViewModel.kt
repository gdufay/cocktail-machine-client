package com.example.cocktailmachine.cocktaillist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cocktailmachine.database.Cocktail
import com.example.cocktailmachine.database.CocktailDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CocktailListViewModel @Inject constructor(database: CocktailDatabase) :
    ViewModel() {

    private val _cocktails = database.cocktailDao().getCocktails()
    val cocktails: LiveData<List<Cocktail>>
        get() = _cocktails
}