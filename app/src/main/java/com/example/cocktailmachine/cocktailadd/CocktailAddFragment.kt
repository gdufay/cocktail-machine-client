package com.example.cocktailmachine.cocktailadd

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktailmachine.R
import com.example.cocktailmachine.databinding.CocktailAddFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

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
class CocktailAddFragment : Fragment(), FragmentResultListener {

    private lateinit var binding: CocktailAddFragmentBinding
    private val addViewModel: CocktailAddViewModel by viewModels()
    private val adapter = CocktailAddAdapter()
    private val getContent = registerForActivityResult(MyOpenDocument(), this::getContentCallback)

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

            event.observe(viewLifecycleOwner, {
                it?.let {
                    handleViewModelEvent(it)
                }
            })
        }
    }

    private fun handleViewModelEvent(code: EventCode) {
        val text = when (code) {
            EventCode.DB_EXCEPTION -> R.string.check_inserted_value
            EventCode.CREATE_SUCCESS -> {
                findNavController().navigateUp()
                R.string.cocktail_added
            }
            EventCode.MISS_FIELD -> R.string.fill_all_fields
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