package com.example.cocktailmachine.database

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["cocktailName"], unique = true)])
data class Cocktail(
    @PrimaryKey(autoGenerate = true) val cocktailId: Int,
    @NonNull var cocktailName: String,
    // @Ignore var picture: Bitmap? // Not used right now
)

@Entity(indices = [Index(value = ["ingredientName"], unique = true)])
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val ingredientId: Int,
    @NonNull var ingredientName: String,
) {
    override fun toString(): String {
        return ingredientName
    }
}

@Entity(
    indices = [Index(value = ["cocktailId", "ingredientId"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = Cocktail::class,
            parentColumns = ["cocktailId"],
            childColumns = ["cocktailId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Ingredient::class,
            parentColumns = ["ingredientId"],
            childColumns = ["ingredientId"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class Quantity(
    @PrimaryKey(autoGenerate = true) val quantityId: Int,
    @NonNull val cocktailId: Int,
    @NonNull val ingredientId: Int,
    @NonNull var quantity: Short
)

data class IngredientWithQuantity(
    val ingredientName: String,
    val quantity: Short
)

/*
// TODO: See to use only relevant data
data class CocktailWithIngredients(
    @Embedded var cocktail: Cocktail,
    @Relation(
        parentColumn = "cocktailId",
        entityColumn = "cocktailId"
    )
    val ingredients: MutableList<Ingredient>
)
 */