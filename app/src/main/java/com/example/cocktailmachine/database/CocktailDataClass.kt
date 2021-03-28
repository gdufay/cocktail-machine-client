package com.example.cocktailmachine.database

import androidx.annotation.NonNull
import androidx.room.*

@Entity(indices = [Index(value = ["cocktailName"], unique = true)])
data class Cocktail(
    @PrimaryKey(autoGenerate = true) val cocktailId: Int,
    @NonNull var cocktailName: String,
    // @Ignore var picture: Bitmap? // Not used right now
)

/*
    Not the most optimal way but easiest way to do it with Room
 */
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Cocktail::class,
            parentColumns = ["cocktailId"],
            childColumns = ["cocktailId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val ingredientId: Int,
    @NonNull val cocktailId: Int,
    @NonNull var ingredientName: String,
    @NonNull var quantity: Short,
)

// TODO: See to use only relevant data
data class CocktailWithIngredients(
    @Embedded var cocktail: Cocktail,
    @Relation(
        parentColumn = "cocktailId",
        entityColumn = "cocktailId"
    )
    val ingredients: MutableList<Ingredient>
)