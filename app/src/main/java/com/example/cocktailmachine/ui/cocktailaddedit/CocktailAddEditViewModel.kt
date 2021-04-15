package com.example.cocktailmachine.ui.cocktailaddedit

import android.database.sqlite.SQLiteException
import android.net.Uri
import android.util.Log
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
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CocktailSettingsViewModel @AssistedInject constructor(
    private val cocktailRepository: CocktailRepository,
    private val ingredientRepository: IngredientRepository,
    @Assisted private val cocktailArg: Cocktail?,
) : ViewModel() {

    private val _cocktail = MutableLiveData(cocktailArg ?: Cocktail(""))
    val cocktail: LiveData<Cocktail>
        get() = _cocktail

    private val _oldQuantities = if (cocktailArg != null) {
        ingredientRepository.getAllIngredientsWithQuantity(cocktailArg.cocktailId).asLiveData()
    } else {
        emptyFlow<List<QuantityIngredientName>>().asLiveData()
    }
    private val _newQuantities = MutableLiveData(mutableListOf<QuantityIngredientName>())

    val quantities = CombinedLiveData(_oldQuantities, _newQuantities) { a, b ->
        listOf(a.orEmpty(), b.orEmpty()).flatten()
    }

    val ingredients = ingredientRepository.getIngredients().asLiveData()

    private val cocktailAddEditEvent = Channel<CocktailAddEditEvent>()
    val settingsEvent = cocktailAddEditEvent.receiveAsFlow()

    private val _fabVisibility = MutableLiveData(View.VISIBLE)
    val fabVisibility: LiveData<Int>
        get() = _fabVisibility

    // TODO: refactor
    fun onFabClick() = viewModelScope.launch {
        try {
            if (cocktailArg == null) {
                createCocktail()
                cocktailAddEditEvent.send(CocktailAddEditEvent.CreateSuccess)
            } else {

                updateCocktail()
                if (_oldQuantities.value != null)
                    updateOldQuantities()

                if (_newQuantities.value != null)
                    insertNewQuantities()

                cocktailAddEditEvent.send(CocktailAddEditEvent.EditSuccess)
            }
        } catch (e: SQLiteException) {
            Log.e("CocktailAddViewModel", "$e")
            cocktailAddEditEvent.send(CocktailAddEditEvent.SQLInsertError)
        }
    }

    fun onClickAddIngredient() {
        val emptyQuantity =
            _newQuantities.value?.any { it.quantity.ingredientId == NO_INGREDIENT_ID } ?: false

        if (emptyQuantity) {
            viewModelScope.launch { cocktailAddEditEvent.send(CocktailAddEditEvent.EmptyQuantity) }
            return
        }

        val new = QuantityIngredientName(Quantity(cocktailArg?.cocktailId ?: 0, NO_INGREDIENT_ID))

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
        _cocktail.value = _cocktail.value?.copy(cocktailUri = uri)
    }

    fun onClickUpdateImage() = viewModelScope.launch {
        cocktailAddEditEvent.send(CocktailAddEditEvent.UpdateImage)
    }

    private suspend fun createCocktail() {
        val (name, uri) = cocktail.value!!
        cocktailRepository.createCocktail(name, uri, _newQuantities.value!!)
    }


    private suspend fun updateCocktail() {
        cocktailRepository.updateCocktail(_cocktail.value!!)
    }

    private suspend fun updateOldQuantities() {
        ingredientRepository.updateQuantity(_oldQuantities.value!!)
    }

    private suspend fun insertNewQuantities() {
        ingredientRepository.insertQuantity(_newQuantities.value!!)
    }

    companion object {
        fun provideFactory(
            assistedFactory: CocktailSettingsViewModelFactory,
            cocktail: Cocktail?,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(cocktail) as T
            }
        }

        const val NO_INGREDIENT_ID = 0
    }

    sealed class CocktailAddEditEvent {
        object EditSuccess : CocktailAddEditEvent()
        object EmptyQuantity : CocktailAddEditEvent()
        object UpdateImage : CocktailAddEditEvent()
        object CreateSuccess : CocktailAddEditEvent()
        object SQLInsertError : CocktailAddEditEvent()
    }
}

@AssistedFactory
interface CocktailSettingsViewModelFactory {
    fun create(cocktailId: Cocktail?): CocktailSettingsViewModel
}