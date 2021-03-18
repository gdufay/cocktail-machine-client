package com.example.cockailmachine.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cockailmachine.R
import com.example.cockailmachine.database.CocktailDatabase
import com.example.cockailmachine.databinding.FragmentRecipeBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RecipeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentRecipeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_recipe, container, false)

        val application = requireActivity().application
        val dataSource = CocktailDatabase.getInstance(application)

        val viewModelFactory = RecipeViewModelFactory(dataSource, application)
        val recipeViewModel =
            ViewModelProvider(this, viewModelFactory).get(RecipeViewModel::class.java)

        binding.lifecycleOwner = this
        binding.recipeViewModel = recipeViewModel

        return binding.root
    }

}