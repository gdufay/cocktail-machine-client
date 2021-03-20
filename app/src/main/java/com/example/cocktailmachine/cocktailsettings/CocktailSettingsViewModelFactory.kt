package com.example.cocktailmachine.cocktailsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CocktailSettingsViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("unchecked_cast")
        if (modelClass.isAssignableFrom(CocktailSettingsViewModel::class.java)) {
            return CocktailSettingsViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}