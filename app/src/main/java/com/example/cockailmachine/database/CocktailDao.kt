package com.example.cockailmachine.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CocktailDao {
    @Insert
    suspend fun insertAll(vararg cocktails: Cocktail)

    @Delete
    suspend fun delete(cocktail: Cocktail)

    @Query("SELECT * FROM cocktail WHERE cocktailId = :key")
    suspend fun get(key: Int): Cocktail?

    @Query("SELECT * FROM Cocktail")
    fun getAll(): LiveData<List<Cocktail>>

    @Transaction
    @Query("SELECT * FROM Cocktail")
    fun getCocktailsWithIngredients(): LiveData<List<CocktailWithIngredients>>
}

@Dao
interface IngredientDao {
    @Insert
    suspend fun insertAll(vararg ingredients: Ingredient)

    @Delete
    suspend fun delete(ingredient: Ingredient)

    @Query("SELECT * from ingredient")
    suspend fun getAll(): List<Ingredient>
}