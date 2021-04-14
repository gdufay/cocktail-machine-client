package com.example.cocktailmachine.data

import java.util.*
import javax.inject.Inject

class IngredientRepository @Inject constructor(private val ingredientDao: IngredientDao) {
    fun getIngredients() = ingredientDao.getIngredients()

    fun getAllIngredientsWithQuantity(cocktailId: Int) =
        ingredientDao.getQuantitiesWithIngredientName(cocktailId)

    // TODO: see to remove
    suspend fun insertIngredient(ingredientName: String) {
        if (ingredientName.isNotBlank()) {
            ingredientDao.insertIngredient(
                Ingredient(ingredientName.toLowerCase(Locale.getDefault()))
            )
        }
    }

    suspend fun insertQuantity(cocktailId: Int, ingredientId: Int, quantity: Short) =
        ingredientDao.insertQuantity(Quantity(cocktailId, ingredientId, quantity))

    @JvmName("insertQuantityListQuantityIngredientName")
    suspend fun insertQuantity(value: List<QuantityIngredientName>) =
        insertQuantity(value.map { it.quantity })

    suspend fun insertQuantity(value: List<Quantity>) = ingredientDao.insertQuantity(value)

    @JvmName("updateQuantityListQuantityIngredientName")
    suspend fun updateQuantity(value: List<QuantityIngredientName>) =
        updateQuantity(value.map { it.quantity })

    suspend fun updateQuantity(value: List<Quantity>) = ingredientDao.updateQuantity(value)
}