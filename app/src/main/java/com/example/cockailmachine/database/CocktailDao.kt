package com.example.cockailmachine.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CocktailDao {
    @Insert
    suspend fun insertAll(vararg cocktails: Cocktail)

    @Delete
    suspend fun delete(cocktail: Cocktail)

    @Query("SELECT * FROM cocktail WHERE id = :key")
    suspend fun get(key: Int): Cocktail?

    @Query("SELECT * FROM cocktail")
    fun getAll(): LiveData<List<Cocktail>>
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

@Dao
interface QuantityDao {
    @Insert
    suspend fun insertAll(vararg quantities: Quantity)

    @Delete
    suspend fun delete(quantity: Quantity)

    @Query("SELECT * from quantity")
    suspend fun getAll(): List<Quantity>
}