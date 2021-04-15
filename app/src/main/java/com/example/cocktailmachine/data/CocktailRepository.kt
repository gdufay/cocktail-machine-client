package com.example.cocktailmachine.data

import android.net.Uri
import androidx.room.withTransaction
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
        quantities: List<QuantityIngredientName>,
    ) =
        database.withTransaction {
            val insertedId = insertCocktail(cocktailName, cocktailUri)
            val q = quantities.map { it.quantity }.map { it.apply { cocktailId = insertedId } }

            insertQuantity(q)
        }

    suspend fun insertCocktail(cocktailName: String, cocktailUri: Uri? = null) =
        cocktailDao.insertCocktail(
            Cocktail(cocktailName.toLowerCase(Locale.getDefault()), cocktailUri)
        ).toInt()

    suspend fun insertQuantity(quantities: List<Quantity>) =
        ingredientDao.insertQuantity(quantities)

    suspend fun updateCocktail(value: Cocktail) =
        cocktailDao.updateCocktail(value)
}