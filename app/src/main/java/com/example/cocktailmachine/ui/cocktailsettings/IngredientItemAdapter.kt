package com.example.cocktailmachine.ui.cocktailsettings

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailmachine.R
import com.example.cocktailmachine.data.Ingredient
import com.example.cocktailmachine.data.QuantityIngredientName
import com.example.cocktailmachine.databinding.IngredientQuantityItemViewBinding

class IngredientItemAdapter :
    ListAdapter<QuantityIngredientName, IngredientItemAdapter.ViewHolder>(DiffCallback()) {

    private var _ingredients: List<Ingredient> = listOf()

    fun setIngredients(ingredients: List<Ingredient>) {
        _ingredients = ingredients
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = IngredientQuantityItemViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = getItem(position)

        holder.bind(ingredient)
    }

    inner class ViewHolder(private val binding: IngredientQuantityItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: QuantityIngredientName) {
            val adapter =
                ArrayAdapter(binding.root.context, R.layout.ingredient_item_view, _ingredients)

            binding.apply {
                val editText = ingredientName.editText as AutoCompleteTextView

                quantity = item
                editText.setAdapter(adapter)
                editText.setOnItemClickListener { _, view, _, _ ->
                    val ingredient: TextView = view.findViewById(R.id.ingredientName)
                    val index = _ingredients.indexOfFirst { it.ingredientName == ingredient.text }

                    if (index != -1)
                        item.quantity.ingredientId = _ingredients[index].ingredientId
                }
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<QuantityIngredientName>() {
        override fun areItemsTheSame(
            oldItem: QuantityIngredientName,
            newItem: QuantityIngredientName,
        ) =
            oldItem.quantity.quantityId == newItem.quantity.quantityId

        override fun areContentsTheSame(
            oldItem: QuantityIngredientName,
            newItem: QuantityIngredientName,
        ) =
            oldItem == newItem
    }
}