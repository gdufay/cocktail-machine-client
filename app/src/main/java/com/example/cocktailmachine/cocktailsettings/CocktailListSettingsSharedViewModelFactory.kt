package com.example.cocktailmachine.cocktailsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cocktailmachine.database.CocktailDatabase

class CocktailListSettingsSharedViewModelFactory(private val database: CocktailDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("unchecked_cast")
        if (modelClass.isAssignableFrom(CocktailListSettingsSharedViewModel::class.java)) {
            return CocktailListSettingsSharedViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}