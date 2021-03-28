package com.example.cocktailmachine.cocktailadd

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailmachine.databinding.FragmentCocktailAddIngredientBinding

class CocktailAddAdapter : RecyclerView.Adapter<CocktailAddAdapter.ViewHolder>() {
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

        holder.bind(item)
    }

    override fun getItemCount() = ingredients.size

    class ViewHolder private constructor(private val binding: FragmentCocktailAddIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: IngredientQuantity) {
            binding.ingredient = item
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