package com.example.cocktailmachine.cocktailadd

import android.database.sqlite.SQLiteException
import android.net.Uri
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.*
import com.example.cocktailmachine.data.CocktailRepository
import com.example.cocktailmachine.data.IngredientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
) :
    ViewModel(), Observable {

    // TODO: use SingleLiveEvent
    private val _toastEvent = MutableLiveData<String>()
    val toastEvent: LiveData<String>
        get() = _toastEvent

    private var _cocktailName: String = ""

    val ingredients = ingredientRepository.getIngredients().asLiveData()

    @Bindable
    fun getCocktailName(): String {
        return _cocktailName
    }

    fun setCocktailName(value: String) {
        _cocktailName = value
    }

    private val _cocktailIngredients = MutableLiveData(listOf(IngredientQuantity()))
    val cocktailIngredients: LiveData<List<IngredientQuantity>>
        get() = _cocktailIngredients

    private val _cocktailUri = MutableLiveData<Uri>()
    val cocktailUri: LiveData<Uri>
        get() = _cocktailUri

    fun setCocktailUri(uri: Uri) {
        _cocktailUri.value = uri
    }

    fun addIngredient() {
        if (_cocktailIngredients.value?.any { x -> x.name.isBlank() || x.quantity.isBlank() } == true) {
            _toastEvent.value = "Fill all ingredients before adding new one"
        } else {
            _cocktailIngredients.value = _cocktailIngredients.value?.plus(IngredientQuantity())
        }
    }

    fun addCocktail() {
        val ingredients = cocktailIngredients.value ?: listOf()

        if (_cocktailName.isBlank() || ingredients.isEmpty()
            || ingredients.any { x -> x.name.isBlank() || x.quantity.isBlank() }
        ) {
            _toastEvent.value = "Fill all fields to add cocktail"
        } else {
            addCocktailDB()
            // TODO: navigate back to list
        }
    }

    private fun addCocktailDB() {
        viewModelScope.launch {
            cocktailIngredients.value?.let {
                try {
                    cocktailRepository.createCocktail(_cocktailName, cocktailUri.value, it)
                    // TODO: get string from res/strings
                    _toastEvent.value = "Cocktail added !"
                } catch (e: SQLiteException) {
                    Log.e("CocktailAddViewModel", "$e")
                    // TODO: get string from res/strings
                    _toastEvent.value = "Please, fill all required value"
                }
            }
        }
    }

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }

    fun newIngredient(ingredientName: String) {
        viewModelScope.launch {
            ingredientRepository.insertIngredient(ingredientName)
        }
    }
}