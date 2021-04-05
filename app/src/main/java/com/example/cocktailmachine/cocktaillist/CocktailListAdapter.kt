package com.example.cocktailmachine.cocktaillist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailmachine.R
import com.example.cocktailmachine.data.Cocktail

class CocktailListAdapter(private val listener: (Cocktail) -> Unit) :
    RecyclerView.Adapter<CocktailListAdapter.ViewHolder>() {
    var cocktails = listOf<Cocktail>()
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
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }


    override fun getItemCount() = cocktails.size

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cocktailImage: ImageView = itemView.findViewById(R.id.item_cocktail_img)
        private val cocktailName: TextView = itemView.findViewById(R.id.item_cocktail_name)


        fun bind(item: Cocktail) {
            cocktailName.text = item.cocktailName
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.cocktail_item_view, parent, false)

                return ViewHolder(view)
            }
        }
    }

}