package com.example.cocktailmachine.ui.cocktailsettings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailmachine.R
import com.example.cocktailmachine.data.IngredientWithQuantity

class IngredientItemAdapter : RecyclerView.Adapter<IngredientItemAdapter.ViewHolder>() {
    var ingredients = listOf<IngredientWithQuantity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = ingredients[position]

        holder.bind(ingredient)
    }

    override fun getItemCount() = ingredients.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ingredientName: TextView = view.findViewById(R.id.item_ingredient_name)
        private val ingredientQuantity: TextView = view.findViewById(R.id.item_ingredient_quantity)

        fun bind(ingredient: IngredientWithQuantity) {
            ingredientName.text = ingredient.ingredientName
            ingredientQuantity.text = ingredient.quantity.toString()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.ingredient_quantity_item_view, parent, false)

                return ViewHolder(view)
            }
        }
    }
}