package com.example.cocktailmachine.cocktailadd

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class IngredientQuantity(
    var name: String = "",
    var quantity: String = "",
)

open class CocktailAddViewModel : ViewModel(), Observable {
    // TODO: use SingleLiveEvent
    private val _toastEvent = MutableLiveData<String>()
    val toastEvent: LiveData<String>
        get() = _toastEvent

    private var _cocktailName: String = ""

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

    // TODO: add error when ingredient not filled
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
            _toastEvent.value = "Cocktail added !"
        }
    }

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with [Bindable] to generate a field in
     * `BR` to be used as `fieldId`.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }
}