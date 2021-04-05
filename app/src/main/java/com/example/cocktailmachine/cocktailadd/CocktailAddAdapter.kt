package com.example.cocktailmachine.cocktailadd

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailmachine.R
import com.example.cocktailmachine.data.Ingredient
import com.example.cocktailmachine.data.Quantity
import com.example.cocktailmachine.databinding.FragmentCocktailAddIngredientBinding

class CocktailAddAdapter : RecyclerView.Adapter<CocktailAddAdapter.ViewHolder>() {
    var quantity = listOf<Quantity>()

    // TODO: remove selected ingredient from list to avoid duplicate (ex: water 5cl and water 10cl)
    var ingredientList = listOf<Ingredient>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var ingredients = listOf<IngredientQuantity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = ingredients[position]

        holder.bind(item, ingredientList)
    }

    override fun getItemCount() = ingredients.size

    class ViewHolder private constructor(private val binding: FragmentCocktailAddIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: IngredientQuantity, ingredients: List<Ingredient>) {
            val adapter =
                ArrayAdapter(binding.root.context, R.layout.ingredient_item_view, ingredients)

            binding.apply {
                ingredient = item
                (ingredientName.editText as AutoCompleteTextView).setAdapter(adapter)
                (ingredientName.editText as AutoCompleteTextView).setOnItemClickListener { _, _, position, _ ->
                    item.ingredientId = ingredients[position].ingredientId
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                return ViewHolder(
                    FragmentCocktailAddIngredientBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }
    }
}