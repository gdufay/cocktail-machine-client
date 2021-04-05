package com.example.cocktailmachine.cocktailadd

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import com.example.cocktailmachine.R
import com.example.cocktailmachine.databinding.CocktailAddFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CocktailAddFragment : Fragment(), FragmentResultListener {

    private lateinit var binding: CocktailAddFragmentBinding
    private val addViewModel: CocktailAddViewModel by viewModels()
    private val adapter = CocktailAddAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.cocktail_add_fragment, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = addViewModel
            ingredientList.adapter = adapter
        }

        viewModelApply()

        setHasOptionsMenu(true)

        childFragmentManager.setFragmentResultListener(
            NewIngredientDialogFragment.ARG_REQUEST_KEY,
            viewLifecycleOwner,
            this
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.navdrawer_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.newIngredient -> {
                NewIngredientDialogFragment().show(
                    childFragmentManager,
                    NewIngredientDialogFragment.TAG
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun viewModelApply() {
        addViewModel.apply {
            cocktailIngredients.observe(viewLifecycleOwner, {
                it?.let {
                    adapter.ingredients = it
                }
            })

            ingredients.observe(viewLifecycleOwner, {
                it?.let {
                    adapter.ingredientList = it
                }
            })

            toastEvent.observe(viewLifecycleOwner, {
                it?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onFragmentResult(requestKey: String, result: Bundle) {
        when (requestKey) {
            NewIngredientDialogFragment.ARG_REQUEST_KEY -> result.getString(
                NewIngredientDialogFragment.ARG_INGREDIENT
            )?.let { addViewModel.newIngredient(it) }
        }
    }
}