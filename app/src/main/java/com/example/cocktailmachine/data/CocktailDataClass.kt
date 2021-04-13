package com.example.cocktailmachine.data

import android.net.Uri
import androidx.annotation.NonNull
import androidx.room.*

@Entity(indices = [Index(value = ["cocktailName"], unique = true)])
data class Cocktail(
    @PrimaryKey(autoGenerate = true) val cocktailId: Int = 0,
    @NonNull var cocktailName: String,
    var cocktailUri: Uri? = null
)

@Entity(indices = [Index(value = ["ingredientName"], unique = true)])
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val ingredientId: Int = 0,
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
    @PrimaryKey(autoGenerate = true) val quantityId: Int = 0,
    @NonNull val cocktailId: Int,
    @NonNull @ColumnInfo(index = true) var ingredientId: Int,
    @NonNull var quantity: Short
)

data class CocktailWithIngredients(
    @Embedded var cocktail: Cocktail,
    @Relation(
        parentColumn = "cocktailId",
        entityColumn = "cocktailId",
        entity = Quantity::class
    )
    val ingredients: List<QuantityIngredientName>
)

data class QuantityIngredientName(
    @Embedded var quantity: Quantity,
    @Relation(
        parentColumn = "ingredientId",
        entityColumn = "ingredientId",
        entity = Ingredient::class,
        projection = ["ingredientName"]
    )
    val ingredientName: String
)