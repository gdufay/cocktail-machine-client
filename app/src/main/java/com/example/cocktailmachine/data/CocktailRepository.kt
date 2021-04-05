package com.example.cocktailmachine.data

import javax.inject.Inject

class CocktailRepository @Inject constructor(private val cocktailDao: CocktailDao) {
    fun getCocktail(cocktailId: Int) = cocktailDao.getCocktail(cocktailId)

    fun getCocktails() = cocktailDao.getCocktails()

    suspend fun insertCocktail(cocktailName: String) =
        cocktailDao.insertCocktail(Cocktail(0, cocktailName))
}