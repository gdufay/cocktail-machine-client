package com.example.cocktailmachine.ui.cocktailsettings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cocktailmachine.R
import com.example.cocktailmachine.databinding.FragmentCocktailSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CocktailSettingsFragment : Fragment(R.layout.fragment_cocktail_settings) {

    private lateinit var binding: FragmentCocktailSettingsBinding
    private val args: CocktailSettingsFragmentArgs by navArgs()
    private val adapter = IngredientItemAdapter()

    @Inject
    lateinit var viewModelFactory: CocktailSettingsViewModelFactory
    private val viewModel: CocktailSettingsViewModel by viewModels {
        CocktailSettingsViewModel.provideFactory(viewModelFactory, args.cocktailId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCocktailSettingsBinding.bind(view)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vModel = viewModel
            ingredientList.adapter = adapter
        }

        viewModelApply()
    }

    private fun viewModelApply() {
        viewModel.apply {
            ingredients.observe(viewLifecycleOwner, {
                it?.let {
                    adapter.ingredients = it
                }
            })

            cocktail.observe(viewLifecycleOwner, {
                Glide.with(requireContext()).load(it?.cocktailUri).centerCrop()
                    .placeholder(R.drawable.ic_insert_photo).into(binding.bigCocktailImg)
            })
        }
    }
}