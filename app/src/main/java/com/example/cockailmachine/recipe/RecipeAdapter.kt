package com.example.cockailmachine.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cockailmachine.R
import com.example.cockailmachine.database.CocktailWithIngredients

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {
    var cocktails = listOf<CocktailWithIngredients>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = cocktails[position]

        holder.bind(item)
    }


    override fun getItemCount() = cocktails.size

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cocktailImage: ImageView = itemView.findViewById(R.id.cocktail_img)
        private val cocktailName: TextView = itemView.findViewById(R.id.cocktail_name)
        private val ingredientName: TextView = itemView.findViewById(R.id.ingredient_name)
        private val quantityValue: TextView = itemView.findViewById(R.id.quantity_value)

        fun bind(item: CocktailWithIngredients) {
            cocktailName.text = item.cocktail.cocktailName
            if (item.ingredients.isNotEmpty()) {
                ingredientName.text = item.ingredients[0].ingredientName
                quantityValue.text = item.ingredients[0].quantity.toString()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.recipe_item_view, parent, false)

                return ViewHolder(view)
            }
        }
    }

}