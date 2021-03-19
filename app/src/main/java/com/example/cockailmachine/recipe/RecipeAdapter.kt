package com.example.cockailmachine.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cockailmachine.R
import com.example.cockailmachine.TextItemViewHolder
import com.example.cockailmachine.database.Cocktail

class RecipeAdapter : RecyclerView.Adapter<TextItemViewHolder>() {
    var data = listOf<Cocktail>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recipe_item_view, parent, false) as TextView

        return TextItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]

        holder.textView.text = item.name
    }

    override fun getItemCount() = data.size
}