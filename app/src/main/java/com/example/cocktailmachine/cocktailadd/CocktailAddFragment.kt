package com.example.cocktailmachine.cocktailadd

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cocktailmachine.R
import com.example.cocktailmachine.databinding.CocktailAddFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

// TODO: move
// TODO: rename
class MyOpenDocument : ActivityResultContracts.OpenDocument() {
    override fun createIntent(context: Context, input: Array<out String>): Intent {
        return super.createIntent(context, input)
            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            .addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            .apply {
                action = Intent.ACTION_OPEN_DOCUMENT
                type = "image/*"
            }
    }
}

@AndroidEntryPoint
class CocktailAddFragment : Fragment(R.layout.cocktail_add_fragment), FragmentResultListener {

    private lateinit var binding: CocktailAddFragmentBinding
    private val addViewModel: CocktailAddViewModel by viewModels()
    private val adapter = CocktailAddAdapter()
    private val getContent = registerForActivityResult(MyOpenDocument(), this::getContentCallback)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = CocktailAddFragmentBinding.bind(view)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = addViewModel
            ingredientList.adapter = adapter
            addCocktailBtn.setOnClickListener {
                getContent.launch(arrayOf("image/*"))
            }
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
            cocktailIngredients.observe(viewLifecycleOwner) {
                adapter.ingredients = it
            }

            ingredients.observe(viewLifecycleOwner) {
                adapter.ingredientList = it
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                addViewModel.addCocktailEvent.collect {
                    handleViewModelEvent(it)
                }
            }
        }
    }

    private fun handleViewModelEvent(event: CocktailAddViewModel.AddCocktailEvent) {
        val text = when (event) {
            is CocktailAddViewModel.AddCocktailEvent.SQLInsertError -> R.string.check_inserted_value
            is CocktailAddViewModel.AddCocktailEvent.CreateCocktailSuccess -> {
                findNavController().navigateUp()
                R.string.cocktail_added
            }
            is CocktailAddViewModel.AddCocktailEvent.MissingField -> R.string.fill_all_fields
        }

        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun onFragmentResult(requestKey: String, result: Bundle) {
        when (requestKey) {
            NewIngredientDialogFragment.ARG_REQUEST_KEY -> {
                result.getString(NewIngredientDialogFragment.ARG_INGREDIENT)?.let {
                    addViewModel.newIngredient(it)
                }
            }
        }
    }

    private fun getContentCallback(uri: Uri?) {
        uri?.let {
            requireContext().contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            addViewModel.setCocktailUri(it)

            binding.addCocktailBtn.visibility = View.GONE
        }
    }
}