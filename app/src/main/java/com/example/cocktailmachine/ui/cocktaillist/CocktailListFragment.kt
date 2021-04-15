package com.example.cocktailmachine.ui.cocktaillist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktailmachine.R
import com.example.cocktailmachine.data.Cocktail
import com.example.cocktailmachine.databinding.FragmentCocktailListBinding
import com.example.cocktailmachine.utils.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CocktailListFragment : Fragment(R.layout.fragment_cocktail_list),
    CocktailListAdapter.OnItemClickListener {

    private lateinit var binding: FragmentCocktailListBinding
    private val viewModel: CocktailListViewModel by viewModels()
    private val adapter = CocktailListAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCocktailListBinding.bind(view)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vModel = viewModel
            recipeList.adapter = adapter

            floatingActionButton.setOnClickListener {
                val action =
                    CocktailListFragmentDirections.actionRecipeFragmentToCocktailSettingsFragment(
                        null)

                findNavController().navigate(action)
            }
        }

        setHasOptionsMenu(true)

        viewModel.cocktails.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cocktail_list_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChanged {
            viewModel.searchQuery.value = it
        }
    }

    override fun onItemClick(cocktail: Cocktail) {
        val action =
            CocktailListFragmentDirections.actionRecipeFragmentToCocktailSettingsFragment(cocktail)

        findNavController().navigate(action)
    }
}