package com.example.cocktailmachine.data

import java.util.*
import javax.inject.Inject

class IngredientRepository @Inject constructor(private val ingredientDao: IngredientDao) {
    fun getIngredients() = ingredientDao.getIngredients()

    fun getAllIngredientsWithQuantity(cocktailId: Int) =
        ingredientDao.getQuantitiesWithIngredientName(cocktailId)

    suspend fun insertIngredient(ingredientName: String) {
        if (ingredientName.isNotBlank()) {
            ingredientDao.insertIngredient(
                Ingredient(
                    0,
                    ingredientName.toLowerCase(Locale.getDefault())
                )
            )
        }
    }

    suspend fun insertQuantity(cocktailId: Int, ingredientId: Int, quantity: Short) {
        ingredientDao.insertQuantity(Quantity(0, cocktailId, ingredientId, quantity))
    }
}