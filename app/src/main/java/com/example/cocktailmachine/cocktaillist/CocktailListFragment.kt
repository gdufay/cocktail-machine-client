package com.example.cocktailmachine.cocktaillist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.cocktailmachine.R
import com.example.cocktailmachine.databinding.FragmentCocktailListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CocktailListFragment : Fragment() {

    private val viewModel: CocktailListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentCocktailListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_cocktail_list, container, false)

        val adapter = CocktailListAdapter() {
            findNavController().navigate(
                CocktailListFragmentDirections.actionRecipeFragmentToCocktailSettingsFragment(
                    it.cocktailId
                )
            )
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vModel = viewModel
            recipeList.adapter = adapter
        }

        viewModel.cocktails.observe(viewLifecycleOwner, {
            it?.let {
                adapter.cocktails = it
            }
        })

        binding.floatingActionButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_recipeFragment_to_cocktailAddFragment))

        return binding.root
    }

}