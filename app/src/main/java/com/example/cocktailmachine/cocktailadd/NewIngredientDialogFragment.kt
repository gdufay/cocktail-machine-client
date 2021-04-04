package com.example.cocktailmachine.cocktailadd

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.cocktailmachine.R

// TODO: move in its proper directory
class NewIngredientDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return context?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.new_ingredient_dialog_view, null))
                .setPositiveButton(R.string.new_ingredient) { _, _ ->
                    val editText: EditText = requireDialog().findViewById(R.id.newIngredient)

                    parentFragmentManager.setFragmentResult(ARG_REQUEST_KEY, Bundle().apply {
                        putString(ARG_INGREDIENT, editText.text.toString())
                    })
                }
                .setNegativeButton(R.string.cancel) { _, _ -> dismiss() }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        const val TAG = "NewIngredientDialog"
        const val ARG_INGREDIENT = "ingredient"
        const val ARG_REQUEST_KEY = "ingredientKey"
    }
}