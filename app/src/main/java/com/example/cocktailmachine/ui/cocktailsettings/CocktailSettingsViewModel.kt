package com.example.cocktailmachine.ui.cocktailsettings

import android.net.Uri
import android.view.View
import androidx.lifecycle.*
import com.example.cocktailmachine.R
import com.example.cocktailmachine.data.*
import com.example.cocktailmachine.utils.CombinedLiveData
import com.example.cocktailmachine.utils.addNewItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CocktailSettingsViewModel @AssistedInject constructor(
    private val cocktailRepository: CocktailRepository,
    private val ingredientRepository: IngredientRepository,
    @Assisted private val cocktailId: Int,
) : ViewModel() {

    private val _sourceCocktail = cocktailRepository.getCocktail(cocktailId).asLiveData()
    private val _cocktailUri = MutableLiveData<Uri>()
    val cocktail = CombinedLiveData(_sourceCocktail, _cocktailUri) { cocktail, uri ->
        cocktail?.apply {
            uri?.let { cocktailUri = it }
        } ?: Cocktail("")
    }

    private val _oldQuantities =
        ingredientRepository.getAllIngredientsWithQuantity(cocktailId).asLiveData()
    private val _newQuantities = MutableLiveData(mutableListOf<QuantityIngredientName>())

    val quantities = CombinedLiveData(_oldQuantities, _newQuantities) { a, b ->
        listOf(a.orEmpty(), b.orEmpty()).flatten()
    }

    val ingredients = ingredientRepository.getIngredients().asLiveData()

    private val cocktailSettingsEvent = Channel<CocktailSettingEvent>()
    val settingsEvent = cocktailSettingsEvent.receiveAsFlow()

    private val _fabVisibility = MutableLiveData(View.VISIBLE)
    val fabVisibility: LiveData<Int>
        get() = _fabVisibility

    fun onFabClick() = viewModelScope.launch {
        cocktailSettingsEvent.send(CocktailSettingEvent.NavigateBack)
        updateCocktail()

        if (_oldQuantities.value != null)
            updateOldQuantities()

        if (_newQuantities.value != null)
            insertNewQuantities()
    }

    fun onClickAddIngredient() {
        val emptyQuantity =
            _newQuantities.value?.any { it.quantity.ingredientId == NO_INGREDIENT_ID } ?: false

        if (emptyQuantity) {
            viewModelScope.launch { cocktailSettingsEvent.send(CocktailSettingEvent.EmptyQuantity) }
            return
        }

        val new = QuantityIngredientName(Quantity(cocktailId, NO_INGREDIENT_ID))

        _newQuantities.addNewItem(new)
    }

    fun requiredCocktailName(v: View) {
        val layout = v as TextInputLayout
        val editText = layout.editText!! as TextInputEditText

        if (editText.text.isNullOrBlank()) {
            layout.error = v.context.getString(R.string.cocktail_name_required)
            _fabVisibility.value = View.GONE
        } else {
            layout.error = null
            _fabVisibility.value = View.VISIBLE
        }
    }

    fun setCocktailUri(uri: Uri) {
        _cocktailUri.value = uri
    }


    fun onClickUpdateImage() = viewModelScope.launch {
        cocktailSettingsEvent.send(CocktailSettingEvent.UpdateImage)
    }

    private fun updateCocktail() = viewModelScope.launch {
        cocktailRepository.updateCocktail(_sourceCocktail.value!!)
    }

    private fun updateOldQuantities() = viewModelScope.launch {
        ingredientRepository.updateQuantity(_oldQuantities.value!!)
    }

    private fun insertNewQuantities() = viewModelScope.launch {
        ingredientRepository.insertQuantity(_newQuantities.value!!)
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

        const val NO_INGREDIENT_ID = 0
    }

    sealed class CocktailSettingEvent {
        object NavigateBack : CocktailSettingEvent()
        object EmptyQuantity : CocktailSettingEvent()
        object UpdateImage : CocktailSettingEvent()
    }
}

@AssistedFactory
interface CocktailSettingsViewModelFactory {
    fun create(cocktailId: Int): CocktailSettingsViewModel
}