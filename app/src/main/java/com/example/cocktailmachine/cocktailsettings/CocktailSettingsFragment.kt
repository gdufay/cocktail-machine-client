package com.example.cocktailmachine.cocktailsettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cocktailmachine.R
import com.example.cocktailmachine.database.CocktailDatabase
import com.example.cocktailmachine.databinding.FragmentCocktailSettingsBinding

class CocktailSettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentCocktailSettingsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_cocktail_settings, container, false)

        val application = requireActivity().application
        val database = CocktailDatabase.getInstance(application)
        val sharedViewModelFactory = CocktailListSettingsSharedViewModelFactory(database)
        val sharedViewModel = ViewModelProvider(requireActivity(), sharedViewModelFactory).get(
            CocktailListSettingsSharedViewModel::class.java
        )

        binding.lifecycleOwner = this
        binding.viewModel = sharedViewModel

        return binding.root
    }
}