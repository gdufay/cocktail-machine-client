package com.example.cocktailmachine.cocktailsettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.cocktailmachine.data.Cocktail
import com.example.cocktailmachine.data.CocktailRepository
import com.example.cocktailmachine.data.IngredientRepository
import com.example.cocktailmachine.data.IngredientWithQuantity
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class CocktailSettingsViewModel @AssistedInject constructor(
    cocktailRepository: CocktailRepository,
    ingredientRepository: IngredientRepository,
    @Assisted private val cocktailId: Int
) : ViewModel() {

    private val _cocktail = cocktailRepository.getCocktail(cocktailId).asLiveData()
    val cocktail: LiveData<Cocktail>
        get() = _cocktail

    private val _ingredients =
        ingredientRepository.getAllIngredientsWithQuantity(cocktailId).asLiveData()
    val ingredients: LiveData<List<IngredientWithQuantity>>
        get() = _ingredients

    companion object {
        fun provideFactory(
            assistedFactory: CocktailSettingsViewModelFactory,
            cocktailId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(cocktailId) as T
            }
        }
    }
}

@AssistedFactory
interface CocktailSettingsViewModelFactory {
    fun create(cocktailId: Int): CocktailSettingsViewModel
}