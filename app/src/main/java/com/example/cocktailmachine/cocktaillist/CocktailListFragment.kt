package com.example.cocktailmachine.cocktaillist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cocktailmachine.R
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
        val dataSource = CocktailDatabase.getInstance(application)

        val viewModelFactory = CocktailListViewModelFactory(dataSource, application)
        val recipeViewModel =
            ViewModelProvider(this, viewModelFactory).get(CocktailListViewModel::class.java)
        val adapter = CocktailListAdapter()

        binding.lifecycleOwner = this
        binding.recipeViewModel = recipeViewModel
        binding.recipeList.adapter = adapter

        recipeViewModel.cocktails.observe(viewLifecycleOwner, {
            it?.let {
                adapter.cocktails = it
            }
        })

        return binding.root
    }

}