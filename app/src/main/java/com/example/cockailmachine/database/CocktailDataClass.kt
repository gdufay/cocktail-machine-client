package com.example.cockailmachine.database

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["name"], unique = true)])
data class Cocktail(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @NonNull var name: String,
    // @Ignore var picture: Bitmap? // Not used right now
)

@Entity(indices = [Index(value = ["name"], unique = true)])
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @NonNull var name: String
)

@Entity(
    primaryKeys = ["cocktailId", "ingredientId"],
    foreignKeys = [
        ForeignKey(
            entity = Cocktail::class,
            parentColumns = ["id"],
            childColumns = ["cocktailId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Ingredient::class,
            parentColumns = ["id"],
            childColumns = ["ingredientId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Quantity(
    val cocktailId: Int,
    val ingredientId: Int,
    @NonNull var quantity: Short,
)

/*
data class CocktailWithIngredients(
    @Embedded val cocktail: Cocktail,
    @Embedded val ingredient: Ingredient,
    val quantity: Int,
)
 */