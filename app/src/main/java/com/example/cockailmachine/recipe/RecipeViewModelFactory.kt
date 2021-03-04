package com.example.cockailmachine.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RecipeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("unchecked_cast")
            if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
                return RecipeViewModel() as T
            }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}