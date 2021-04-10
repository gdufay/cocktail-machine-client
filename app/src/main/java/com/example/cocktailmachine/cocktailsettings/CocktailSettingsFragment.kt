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
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CocktailSettingsFragment : Fragment() {

    private lateinit var binding: FragmentCocktailSettingsBinding
    private val args: CocktailSettingsFragmentArgs by navArgs()
    private val adapter = IngredientItemAdapter()

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
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_cocktail_settings, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                it?.let {
                    it.cocktailUri?.let { uri ->
                        Picasso.get().load(uri).into(binding.bigCocktailImg)
                    }
                }
            })
        }
    }
}