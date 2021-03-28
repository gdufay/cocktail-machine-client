package com.example.cocktailmachine.cocktailsettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cocktailmachine.R
import com.example.cocktailmachine.databinding.FragmentCocktailSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CocktailSettingsFragment : Fragment() {

    private val sharedViewModel: CocktailListSettingsSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentCocktailSettingsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_cocktail_settings, container, false)

        val adapter = IngredientItemAdapter()

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            ingredientList.adapter = adapter
        }

        sharedViewModel.selectedCocktail.observe(viewLifecycleOwner, {
            it?.let {
                adapter.ingredients = it.ingredients
            }
        })

        return binding.root
    }

}