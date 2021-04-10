package com.example.cocktailmachine.cocktaillist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailmachine.R
import com.example.cocktailmachine.data.Cocktail
import com.squareup.picasso.Picasso

class CocktailListAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Cocktail, CocktailListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.cocktail_item_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }

    inner class ViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val cocktailImage: ImageView = itemView.findViewById(R.id.item_cocktail_img)
        private val cocktailName: TextView = itemView.findViewById(R.id.item_cocktail_name)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position))
                }
            }
        }

        fun bind(item: Cocktail) {
            cocktailName.text = item.cocktailName

            if (item.cocktailUri != null) {
                Picasso.get().load(item.cocktailUri!!).into(cocktailImage)
            } else {
                cocktailImage.setImageResource(R.drawable.ic_insert_photo)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(cocktail: Cocktail)
    }

    class DiffCallback : DiffUtil.ItemCallback<Cocktail>() {
        override fun areItemsTheSame(oldItem: Cocktail, newItem: Cocktail) =
            oldItem.cocktailId == newItem.cocktailId

        override fun areContentsTheSame(oldItem: Cocktail, newItem: Cocktail) =
            oldItem == newItem
    }
}