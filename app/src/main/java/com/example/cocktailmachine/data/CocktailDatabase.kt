package com.example.cocktailmachine.data

import android.net.Uri
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(
    entities = [Cocktail::class, Ingredient::class, Quantity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class CocktailDatabase : RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
    abstract fun ingredientDao(): IngredientDao
}

// TODO: move
class Converters {
    @TypeConverter
    fun fromUri(value: String?): Uri? {
        return value?.let { Uri.parse(it) }
    }

    @TypeConverter
    fun uriToString(uri: Uri?): String? {
        return uri?.toString()
    }
}