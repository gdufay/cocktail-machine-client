package com.example.cocktailmachine.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCocktail(cocktail: Cocktail): Long

    @Delete
    suspend fun deleteCocktail(cocktail: Cocktail)

    @Query("SELECT * FROM Cocktail WHERE cocktailId = :key")
    fun getCocktail(key: Int): Flow<Cocktail>

    @Query("SELECT * FROM Cocktail WHERE cocktailName LIKE '%' || :query || '%'")
    fun getCocktails(query: String): Flow<List<Cocktail>>

    @Query(
        "SELECT * from Cocktail as c "
                + " INNER JOIN Quantity as q ON c.cocktailId = q.cocktailId"
                + " INNER JOIN Ingredient as i ON q.ingredientId = i.ingredientId AND ingredientName IN (:ingredients)"
                + " WHERE cocktailName LIKE '%' || :query || '%'"
    )
    fun getCocktailsFilteredByIngredient(query: String, ingredients: List<String>): Flow<List<Cocktail>>
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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertListQuantity(quantity: List<Quantity>)
}