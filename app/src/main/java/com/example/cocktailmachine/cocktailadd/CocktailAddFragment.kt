package com.example.cocktailmachine.cocktailadd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cocktailmachine.R
import com.example.cocktailmachine.databinding.CocktailAddFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CocktailAddFragment : Fragment() {

    private val addViewModel: CocktailAddViewModel by viewModels()
    private val adapter = CocktailAddAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: CocktailAddFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.cocktail_add_fragment, container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = addViewModel
            ingredientList.adapter = adapter
        }

        viewModelApply()

        return binding.root
    }

    private fun viewModelApply() {
        addViewModel.apply {
            cocktailIngredients.observe(viewLifecycleOwner, {
                it?.let {
                    adapter.ingredients = it
                }
            })

            ingredients.observe(viewLifecycleOwner, {
                it?.let {
                    adapter.foo = it
                }
            })

            toastEvent.observe(viewLifecycleOwner, {
                it?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}