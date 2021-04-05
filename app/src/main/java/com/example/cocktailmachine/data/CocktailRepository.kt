package com.example.cocktailmachine.data

import java.util.*
import javax.inject.Inject

class CocktailRepository @Inject constructor(private val cocktailDao: CocktailDao) {
    fun getCocktail(cocktailId: Int) = cocktailDao.getCocktail(cocktailId)

    fun getCocktails() = cocktailDao.getCocktails()

    suspend fun insertCocktail(cocktailName: String): Long {
            return cocktailDao.insertCocktail(
                Cocktail(
                    0,
                    cocktailName.toLowerCase(Locale.getDefault())
                )
            )
        }
}