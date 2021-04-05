package com.example.cocktailmachine.data

import java.util.*
import javax.inject.Inject

class CocktailRepository @Inject constructor(private val cocktailDao: CocktailDao) {
    fun getCocktail(cocktailId: Int) = cocktailDao.getCocktail(cocktailId)

    fun getCocktails() = cocktailDao.getCocktails()

    @Throws(IllegalArgumentException::class)
    suspend fun insertCocktail(cocktailName: String): Long {
        // TODO: handle blank cocktail name
        if (cocktailName.isNotBlank()) {
            return cocktailDao.insertCocktail(
                Cocktail(
                    0,
                    cocktailName.toLowerCase(Locale.getDefault())
                )
            )
        }
        throw IllegalArgumentException("cocktailName shouldn't be blank")
    }
}