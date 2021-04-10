package com.example.cocktailmachine.cocktailadd

import android.database.sqlite.SQLiteException
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.example.cocktailmachine.data.CocktailRepository
import com.example.cocktailmachine.data.IngredientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class IngredientQuantity(
    var ingredientId: Int = 0,
    var name: String = "",
    var quantity: String = "",
)

@HiltViewModel
open class CocktailAddViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    private val ingredientRepository: IngredientRepository
) : ViewModel() {

    val cocktailName = MutableLiveData("")

    val ingredients = ingredientRepository.getIngredients().asLiveData()

    private val _cocktailIngredients = MutableLiveData(listOf(IngredientQuantity()))
    val cocktailIngredients: LiveData<List<IngredientQuantity>>
        get() = _cocktailIngredients

    private val _cocktailUri = MutableLiveData<Uri>()
    val cocktailUri: LiveData<Uri>
        get() = _cocktailUri

    private val addCocktailEventChannel = Channel<AddCocktailEvent>()
    val addCocktailEvent = addCocktailEventChannel.receiveAsFlow()

    fun setCocktailUri(uri: Uri) {
        _cocktailUri.value = uri
    }

    fun addIngredient() = viewModelScope.launch {
        if (_cocktailIngredients.value?.any { x -> x.name.isBlank() || x.quantity.isBlank() } == true) {
            addCocktailEventChannel.send(AddCocktailEvent.MissingField)
        } else {
            _cocktailIngredients.value = _cocktailIngredients.value?.plus(IngredientQuantity())
        }
    }

    fun addCocktail() = viewModelScope.launch {
        val ingredients = cocktailIngredients.value ?: listOf()

        if (cocktailName.value.isNullOrBlank() || ingredients.isEmpty()
            || ingredients.any { x -> x.name.isBlank() || x.quantity.isBlank() }
        ) {
            // TODO: display error
            addCocktailEventChannel.send(AddCocktailEvent.MissingField)
        } else {
            addCocktailDB()
        }
    }

    private fun addCocktailDB() = viewModelScope.launch {
        try {
            cocktailRepository.createCocktail(
                cocktailName.value!!,
                cocktailUri.value,
                cocktailIngredients.value!!
            )
            addCocktailEventChannel.send(AddCocktailEvent.CreateCocktailSuccess)
        } catch (e: SQLiteException) {
            Log.e("CocktailAddViewModel", "$e")
            addCocktailEventChannel.send(AddCocktailEvent.SQLInsertError)
        }
    }

    fun newIngredient(ingredientName: String) = viewModelScope.launch {
        ingredientRepository.insertIngredient(ingredientName)
    }

    sealed class AddCocktailEvent {
        object CreateCocktailSuccess : AddCocktailEvent()
        object SQLInsertError : AddCocktailEvent()
        object MissingField : AddCocktailEvent()
    }
}