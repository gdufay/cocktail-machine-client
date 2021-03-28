package com.example.cocktailmachine.cocktailadd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cocktailmachine.R
import com.example.cocktailmachine.databinding.CocktailAddFragmentBinding

class CocktailAddFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: CocktailAddFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.cocktail_add_fragment, container, false)

        /*
        val application = requireActivity().application
        val database = CocktailDatabase.getInstance(application)
         */
        val viewModel = ViewModelProvider(this).get(CocktailAddViewModel::class.java)

        val adapter = CocktailAddAdapter()

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.ingredientList.adapter = adapter

        viewModel.cocktailIngredients.observe(viewLifecycleOwner, {
            it?.let {
                adapter.ingredients = it
            }
        })

        viewModel.toastEvent.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }

}