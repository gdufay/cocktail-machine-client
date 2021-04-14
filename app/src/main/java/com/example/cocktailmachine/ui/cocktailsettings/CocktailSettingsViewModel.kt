package com.example.cocktailmachine.ui.cocktailsettings

import androidx.lifecycle.*
import com.example.cocktailmachine.data.CocktailRepository
import com.example.cocktailmachine.data.IngredientRepository
import com.example.cocktailmachine.data.Quantity
import com.example.cocktailmachine.data.QuantityIngredientName
import com.example.cocktailmachine.utils.CombinedLiveData
import com.example.cocktailmachine.utils.addNewItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class CocktailSettingsViewModel @AssistedInject constructor(
    cocktailRepository: CocktailRepository,
    ingredientRepository: IngredientRepository,
    @Assisted private val cocktailId: Int,
) : ViewModel() {

    val cocktail = cocktailRepository.getCocktail(cocktailId).asLiveData()

    private val _oldQuantities =
        ingredientRepository.getAllIngredientsWithQuantity(cocktailId).asLiveData()
    private val _newQuantities = MutableLiveData(mutableListOf<QuantityIngredientName>())

    val quantities = CombinedLiveData(_oldQuantities, _newQuantities) { a, b ->
        listOf(a.orEmpty(), b.orEmpty()).flatten()
    }

    val ingredients = ingredientRepository.getIngredients().asLiveData()

    private val cocktailSettingsEvent = Channel<CocktailSettingEvent>()
    val settingsEvent = cocktailSettingsEvent.receiveAsFlow()

    fun onFabClick() = viewModelScope.launch {
        cocktailSettingsEvent.send(CocktailSettingEvent.NavigateBack)
        // update _cocktail
        // update _quantitiesRepo
        // insert _quantities
    }

    fun onClickAddIngredient() {
        // create random id to avoid bug in IngredientItemAdapter DiffCallback
        val quantityId = Random.nextInt()
        val new = QuantityIngredientName(Quantity(cocktailId, 0, quantityId = quantityId))

        _newQuantities.addNewItem(new)
    }

    companion object {
        fun provideFactory(
            assistedFactory: CocktailSettingsViewModelFactory,
            cocktailId: Int,
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