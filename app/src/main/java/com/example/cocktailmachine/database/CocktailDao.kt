package com.example.cocktailmachine.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CocktailDao {
    @Insert
    suspend fun insertCocktail(vararg cocktails: Cocktail)

    @Delete
    suspend fun deleteCocktail(cocktail: Cocktail)

    @Query("SELECT * FROM Cocktail WHERE cocktailId = :key")
    fun getCocktail(key: Int): LiveData<Cocktail>

    @Query("SELECT * FROM Cocktail")
    fun getCocktails(): LiveData<List<Cocktail>>

}

@Dao
interface IngredientDao {
    @Insert
    suspend fun insertIngredient(vararg ingredients: Ingredient)

    @Delete
    suspend fun deleteIngredient(ingredient: Ingredient)

    @Query("SELECT * FROM ingredient WHERE ingredientId = :key")
    suspend fun getIngredient(key: Int): Ingredient?

    @Query("SELECT * FROM Ingredient")
    fun getIngredients(): LiveData<List<Ingredient>>

    @Query(
        "SELECT ingredientName, quantity FROM Quantity as q"
                + " INNER JOIN Ingredient as i ON q.ingredientId = i.ingredientId"
                + " WHERE cocktailId = :cocktailId"
    )
    fun getAllIngredientsWithQuantity(cocktailId: Int): LiveData<List<IngredientWithQuantity>>
}