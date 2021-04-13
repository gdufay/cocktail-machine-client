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
        ingredients: List<IngredientQuantity>
    ) {
        database.withTransaction {
            val cocktailId = insertCocktail(cocktailName, cocktailUri).toInt()

            insertListQuantity(cocktailId, ingredients)
        }
    }

    suspend fun insertCocktail(cocktailName: String, cocktailUri: Uri? = null): Long {
        return cocktailDao.insertCocktail(
            Cocktail(
                cocktailName = cocktailName.toLowerCase(Locale.getDefault()),
                cocktailUri = cocktailUri
            )
        )
    }

    suspend fun insertListQuantity(cocktailId: Int, quantities: List<IngredientQuantity>) {
        insertListQuantity(
            quantities.map {
                Quantity(
                    cocktailId = cocktailId,
                    ingredientId = it.ingredientId,
                    quantity = it.quantity.toShort()
                )
            }
        )
    }

    suspend fun insertListQuantity(quantities: List<Quantity>) =
        ingredientDao.insertListQuantity(quantities)
}