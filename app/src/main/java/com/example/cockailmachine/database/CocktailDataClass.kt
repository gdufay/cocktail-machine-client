package com.example.cockailmachine.database

import android.graphics.Bitmap
import androidx.room.*

@Entity
data class Cocktail(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var name: String,
    // @Ignore var picture: Bitmap? // Not used right now
)

@Entity
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var name: String
)

@Entity(primaryKeys = ["cocktailId", "ingredientId"])
data class Quantity(
    val cocktailId: Int,
    val ingredientId: Int,
    var quantity: Short,
)

/*
data class CocktailWithIngredients(
    @Embedded val cocktail: Cocktail,
    @Embedded val ingredient: Ingredient,
    val quantity: Int,
)
 */