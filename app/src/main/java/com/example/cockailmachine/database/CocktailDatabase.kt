package com.example.cockailmachine.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Cocktail::class, Ingredient::class],
    version = 1,
    exportSchema = true
)
abstract class CocktailDatabase : RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
    abstract fun ingredientDao(): IngredientDao
    // abstract fun quantityDao(): QuantityDao

    companion object {
        @Volatile
        private var INSTANCE: CocktailDatabase? = null

        fun getInstance(context: Context): CocktailDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CocktailDatabase::class.java,
                    "cocktail_database"
                )
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}