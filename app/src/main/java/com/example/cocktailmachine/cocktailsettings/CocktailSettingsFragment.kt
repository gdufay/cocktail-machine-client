package com.example.cocktailmachine.cocktailsettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.cocktailmachine.R
import com.example.cocktailmachine.databinding.FragmentCocktailSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CocktailSettingsFragment : Fragment() {

    private val args: CocktailSettingsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: CocktailSettingsViewModelFactory

    private val viewModel: CocktailSettingsViewModel by viewModels {
        CocktailSettingsViewModel.provideFactory(viewModelFactory, args.cocktailId)
    }

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
            vModel = viewModel
            ingredientList.adapter = adapter
        }

        viewModel.ingredients.observe(viewLifecycleOwner, {
            it?.let {
                adapter.ingredients = it
            }
        })

        return binding.root
    }

}