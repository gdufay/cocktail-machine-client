package com.example.cocktailmachine.cocktailadd

import android.database.sqlite.SQLiteException
import android.net.Uri
import android.util.Log
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
) : ViewModel() {

    val cocktailName = MutableLiveData("")

    val ingredients = ingredientRepository.getIngredients().asLiveData()

    private val _cocktailIngredients = MutableLiveData(listOf(IngredientQuantity()))
    val cocktailIngredients: LiveData<List<IngredientQuantity>>
        get() = _cocktailIngredients

    private val _cocktailUri = MutableLiveData<Uri>()
    val cocktailUri: LiveData<Uri>
        get() = _cocktailUri

    private val _event = MutableLiveData<EventCode>()
    val event: LiveData<EventCode>
        get() = _event

    fun setCocktailUri(uri: Uri) {
        _cocktailUri.value = uri
    }

    fun addIngredient() {
        if (_cocktailIngredients.value?.any { x -> x.name.isBlank() || x.quantity.isBlank() } == true) {
            _event.value = EventCode.MISS_FIELD
        } else {
            _cocktailIngredients.value = _cocktailIngredients.value?.plus(IngredientQuantity())
        }
    }

    fun addCocktail() {
        val ingredients = cocktailIngredients.value ?: listOf()

        if (cocktailName.value.isNullOrBlank() || ingredients.isEmpty()
            || ingredients.any { x -> x.name.isBlank() || x.quantity.isBlank() }
        ) {
            // TODO: display error
            _event.value = EventCode.MISS_FIELD
        } else {
            addCocktailDB()
        }
    }

    private fun addCocktailDB() {
        viewModelScope.launch {
            try {
                cocktailRepository.createCocktail(
                    cocktailName.value!!,
                    cocktailUri.value,
                    cocktailIngredients.value!!
                )
                _event.value = EventCode.CREATE_SUCCESS
            } catch (e: SQLiteException) {
                Log.e("CocktailAddViewModel", "$e")
                _event.value = EventCode.DB_EXCEPTION
            }
        }
    }

    fun newIngredient(ingredientName: String) {
        viewModelScope.launch {
            ingredientRepository.insertIngredient(ingredientName)
        }
    }
}

enum class EventCode { CREATE_SUCCESS, MISS_FIELD, DB_EXCEPTION }
