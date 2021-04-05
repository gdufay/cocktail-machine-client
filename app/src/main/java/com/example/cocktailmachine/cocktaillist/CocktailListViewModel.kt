package com.example.cocktailmachine.cocktaillist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.cocktailmachine.data.Cocktail
import com.example.cocktailmachine.data.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CocktailListViewModel @Inject constructor(cocktailRepository: CocktailRepository) :
    ViewModel() {

    private val _cocktails = cocktailRepository.getCocktails().asLiveData()
    val cocktails: LiveData<List<Cocktail>>
        get() = _cocktails
}