package com.example.cocktailmachine.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCocktail(cocktail: Cocktail): Long

    @Delete
    suspend fun deleteCocktail(cocktail: Cocktail)

    @Query("SELECT * FROM Cocktail WHERE cocktailId = :key")
    fun getCocktail(key: Int): Flow<Cocktail>

    @Query("SELECT * FROM Cocktail")
    fun getCocktails(): Flow<List<Cocktail>>

}

@Dao
interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIngredient(ingredient: Ingredient)

    @Delete
    suspend fun deleteIngredient(ingredient: Ingredient)

    @Query("SELECT * FROM ingredient WHERE ingredientId = :key")
    suspend fun getIngredient(key: Int): Ingredient?

    @Query("SELECT * FROM Ingredient")
    fun getIngredients(): Flow<List<Ingredient>>

    @Query(
        "SELECT ingredientName, quantity FROM Quantity as q"
                + " INNER JOIN Ingredient as i ON q.ingredientId = i.ingredientId"
                + " WHERE cocktailId = :cocktailId"
    )
    fun getAllIngredientsWithQuantity(cocktailId: Int): Flow<List<IngredientWithQuantity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuantity(vararg quantity: Quantity)
}