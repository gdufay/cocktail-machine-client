package com.example.cockailmachine.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecipeViewModel : ViewModel() {
    private val _recipes = MutableLiveData<String>()

    val recipes: LiveData<String>
        get() = _recipes

    init {
        _recipes.value = "Test1"
    }
}