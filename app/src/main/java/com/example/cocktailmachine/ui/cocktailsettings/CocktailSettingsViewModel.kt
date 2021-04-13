package com.example.cocktailmachine.ui.cocktailsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocktailmachine.data.CocktailRepository
import com.example.cocktailmachine.data.IngredientRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CocktailSettingsViewModel @AssistedInject constructor(
    cocktailRepository: CocktailRepository,
    ingredientRepository: IngredientRepository,
    @Assisted private val cocktailId: Int
) : ViewModel() {

    val cocktail = cocktailRepository.getCocktailWithIngredient(cocktailId).asLiveData()

    val ingredients = ingredientRepository.getIngredients().asLiveData()

    private val cocktailSettingsEvent = Channel<CocktailSettingEvent>()
    val settingsEvent = cocktailSettingsEvent.receiveAsFlow()

    fun onFabClick() = viewModelScope.launch {
        cocktailSettingsEvent.send(CocktailSettingEvent.NavigateBack)
    }

    fun onClickAddIngredient() {
    }

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

    sealed class CocktailSettingEvent {
        object NavigateBack : CocktailSettingEvent()
    }
}

@AssistedFactory
interface CocktailSettingsViewModelFactory {
    fun create(cocktailId: Int): CocktailSettingsViewModel
}