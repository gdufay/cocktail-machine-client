package com.example.cocktailmachine.cocktaillist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.cocktailmachine.R
import com.example.cocktailmachine.cocktailsettings.CocktailListSettingsSharedViewModel
import com.example.cocktailmachine.cocktailsettings.CocktailListSettingsSharedViewModelFactory
import com.example.cocktailmachine.database.CocktailDatabase
import com.example.cocktailmachine.databinding.FragmentCocktailListBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CocktailListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentCocktailListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_cocktail_list, container, false)

        val application = requireActivity().application
        val database = CocktailDatabase.getInstance(application)
        val sharedViewModelFactory = CocktailListSettingsSharedViewModelFactory(database)
        val sharedViewModel = ViewModelProvider(requireActivity(), sharedViewModelFactory).get(
            CocktailListSettingsSharedViewModel::class.java
        )

        val adapter = CocktailListAdapter() {
            sharedViewModel.selectCocktail(it)
            findNavController().navigate(R.id.action_recipeFragment_to_cocktailSettingsFragment)
        }

        binding.lifecycleOwner = this
        binding.viewModel = sharedViewModel
        binding.recipeList.adapter = adapter

        sharedViewModel.cocktails.observe(viewLifecycleOwner, {
            it?.let {
                adapter.cocktails = it
            }
        })

        return binding.root
    }

}