package com.example.cocktailmachine.cocktaillist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.cocktailmachine.R
import com.example.cocktailmachine.cocktailsettings.CocktailListSettingsSharedViewModel
import com.example.cocktailmachine.databinding.FragmentCocktailListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CocktailListFragment : Fragment() {

    private val sharedViewModel: CocktailListSettingsSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentCocktailListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_cocktail_list, container, false)

        val adapter = CocktailListAdapter() {
            sharedViewModel.selectCocktail(it)
            findNavController().navigate(R.id.action_recipeFragment_to_cocktailSettingsFragment)
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            recipeList.adapter = adapter
        }

        sharedViewModel.cocktails.observe(viewLifecycleOwner, {
            it?.let {
                adapter.cocktails = it
            }
        })

        // TODO: see to move
        binding.floatingActionButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_recipeFragment_to_cocktailAddFragment))

        return binding.root
    }

}