package com.example.cocktailmachine.data

import android.net.Uri
import androidx.room.withTransaction
import com.example.cocktailmachine.ui.cocktailadd.IngredientQuantity
import java.util.*
import javax.inject.Inject

class CocktailRepository @Inject constructor(private val database: CocktailDatabase) {
    private val cocktailDao = database.cocktailDao()
    private val ingredientDao = database.ingredientDao()

    fun getCocktail(cocktailId: Int) = cocktailDao.getCocktail(cocktailId)

    fun getCocktails(query: String = "") = cocktailDao.getCocktails(query)

    fun getCocktailWithIngredient(cocktailId: Int) =
        cocktailDao.getCocktailWithIngredients(cocktailId)

    suspend fun createCocktail(
        cocktailName: String,
        cocktailUri: Uri?,
        ingredients: List<IngredientQuantity>,
    ) =
        database.withTransaction {
            val cocktailId = insertCocktail(cocktailName, cocktailUri).toInt()

            insertQuantity(cocktailId, ingredients)
        }

    suspend fun insertCocktail(cocktailName: String, cocktailUri: Uri? = null) =
        cocktailDao.insertCocktail(
            Cocktail(cocktailName.toLowerCase(Locale.getDefault()), cocktailUri)
        )

    suspend fun insertQuantity(cocktailId: Int, quantities: List<IngredientQuantity>) =
        insertQuantity(
            quantities.map { Quantity(cocktailId, it.ingredientId, it.quantity.toShort()) }
        )

    suspend fun insertQuantity(quantities: List<Quantity>) =
        ingredientDao.insertQuantity(quantities)

    suspend fun updateCocktail(value: Cocktail) =
        cocktailDao.updateCocktail(value)
}