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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: CocktailAddFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.cocktail_add_fragment, container, false)

        val adapter = CocktailAddAdapter()

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = addViewModel
            ingredientList.adapter = adapter
        }

        addViewModel.cocktailIngredients.observe(viewLifecycleOwner, {
            it?.let {
                adapter.ingredients = it
            }
        })

        addViewModel.toastEvent.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }

}