package com.example.cocktailmachine.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Cocktail::class, Ingredient::class, Quantity::class],
    version = 1,
    exportSchema = true
)
abstract class CocktailDatabase : RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
    abstract fun ingredientDao(): IngredientDao
}