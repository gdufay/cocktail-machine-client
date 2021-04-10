package com.example.cocktailmachine.data

import android.net.Uri
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cocktailmachine.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [Cocktail::class, Ingredient::class, Quantity::class],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class CocktailDatabase : RoomDatabase() {

    abstract fun cocktailDao(): CocktailDao
    abstract fun ingredientDao(): IngredientDao

    class Callback @Inject constructor(
        private val database: Provider<CocktailDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val cocktailDao = database.get().cocktailDao()
            val ingredientDao = database.get().ingredientDao()

            val ingredients = listOf("bière", "eau", "rhum", "vodka", "sirop de grenadine")
            val cocktails = listOf(
                Pair(
                    "mojito",
                    "https://st.depositphotos.com/1367205/3711/i/950/depositphotos_37116235-stock-photo-mojito-cocktail.jpg"
                ),
            )
            val quantities = listOf<Triple<Int, Int, Short>>(
                Triple(1, 3, 4)
            )

            applicationScope.launch {
                // insert ingredient
                for (ingredient in ingredients)
                    ingredientDao.insertIngredient(Ingredient(ingredientName = ingredient))

                for (cocktail in cocktails)
                    cocktailDao.insertCocktail(
                        Cocktail(
                            cocktailName = cocktail.first,
                            cocktailUri = Uri.parse(cocktail.second)
                        )
                    )

                for (quantity in quantities)
                    ingredientDao.insertQuantity(
                        Quantity(
                            cocktailId = quantity.first,
                            ingredientId = quantity.second,
                            quantity = quantity.third
                        )
                    )
            }
        }
    }
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