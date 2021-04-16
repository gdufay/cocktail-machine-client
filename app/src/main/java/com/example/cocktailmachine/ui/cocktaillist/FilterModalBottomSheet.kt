package com.example.cocktailmachine.ui.cocktaillist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailmachine.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

class FilterModalBottomSheet(listener: FilterAdapter.OnItemClickListener) :
    BottomSheetDialogFragment() {
    private val adapter = FilterAdapter(listener)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? =
        inflater.inflate(R.layout.filter_modal_bottom_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.filtersList)
        recyclerView.adapter = adapter
    }

    fun setAdapterValue(value: List<IngredientFilter>) {
        adapter.submitList(value)
    }

    companion object {
        const val TAG = "FilterModalBottomSheet"
    }
}

class FilterAdapter(private val listener: OnItemClickListener) :
    ListAdapter<IngredientFilter, FilterAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.filter_modal_bottom_sheet_chip, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }

    interface OnItemClickListener {
        fun onChipClick(ingredient: Int, isChecked: Boolean)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val chip: Chip = itemView.findViewById(R.id.filter)

        fun bind(item: IngredientFilter) {
            chip.apply {
                text = item.ingredientName
                isChecked = item.isChecked
                setOnCheckedChangeListener { _, isChecked ->
                    item.isChecked = isChecked
                    listener.onChipClick(item.ingredientId, isChecked)
                }
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<IngredientFilter>() {
        override fun areItemsTheSame(
            oldItem: IngredientFilter,
            newItem: IngredientFilter,
        ): Boolean =
            oldItem.ingredientId == newItem.ingredientId

        override fun areContentsTheSame(
            oldItem: IngredientFilter,
            newItem: IngredientFilter,
        ): Boolean =
            oldItem == newItem
    }
}