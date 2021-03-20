package com.example.cocktailmachine.cocktailsettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.cocktailmachine.R
import com.example.cocktailmachine.databinding.FragmentCocktailSettingsBinding

class CocktailSettingsFragment : Fragment() {
    private val sharedViewModel: CocktailListSettingsSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentCocktailSettingsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_cocktail_settings, container, false)

        val viewModelFactory = CocktailSettingsViewModelFactory()
        val cocktailSettingsViewModel =
            ViewModelProvider(this, viewModelFactory).get(CocktailSettingsViewModel::class.java)

        binding.lifecycleOwner = this
        binding.cocktailSettingsViewModel = cocktailSettingsViewModel

        sharedViewModel.selected.observe(viewLifecycleOwner, {
            cocktailSettingsViewModel.setCocktail(it)
        })


        return binding.root
    }
}