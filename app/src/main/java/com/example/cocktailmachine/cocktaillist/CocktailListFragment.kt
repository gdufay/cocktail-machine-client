package com.example.cocktailmachine.cocktaillist

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.cocktailmachine.R
import com.example.cocktailmachine.data.Cocktail
import com.example.cocktailmachine.databinding.FragmentCocktailListBinding
import com.example.cocktailmachine.utils.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CocktailListFragment : Fragment(), CocktailListAdapter.OnItemClickListener {

    private lateinit var binding: FragmentCocktailListBinding
    private val viewModel: CocktailListViewModel by viewModels()
    private val adapter = CocktailListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_cocktail_list, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vModel = viewModel
            recipeList.adapter = adapter

            floatingActionButton.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_recipeFragment_to_cocktailAddFragment)
            )
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(cocktail: Cocktail) {
        findNavController().navigate(
            CocktailListFragmentDirections.actionRecipeFragmentToCocktailSettingsFragment(
                cocktail.cocktailId
            )
        )
    }
}