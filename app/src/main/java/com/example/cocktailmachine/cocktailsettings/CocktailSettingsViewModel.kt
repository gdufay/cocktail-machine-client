package com.example.cocktailmachine.cocktailsettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cocktailmachine.database.Cocktail
import com.example.cocktailmachine.database.CocktailDatabase
import com.example.cocktailmachine.database.IngredientWithQuantity
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class CocktailSettingsViewModel @AssistedInject constructor(
    dataSource: CocktailDatabase,
    @Assisted private val cocktailId: Int
) : ViewModel() {

    private val _cocktail = dataSource.cocktailDao().getCocktail(cocktailId)
    val cocktail: LiveData<Cocktail>
        get() = _cocktail

    private val _ingredients = dataSource.ingredientDao().getAllIngredientsWithQuantity(cocktailId)
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