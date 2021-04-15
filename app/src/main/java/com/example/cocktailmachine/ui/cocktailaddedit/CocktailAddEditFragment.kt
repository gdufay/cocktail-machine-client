package com.example.cocktailmachine.ui.cocktailaddedit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cocktailmachine.R
import com.example.cocktailmachine.databinding.FragmentCocktailAddEditBinding
import com.example.cocktailmachine.ui.cocktailaddedit.CocktailSettingsViewModel.CocktailAddEditEvent
import com.example.cocktailmachine.utils.CustomOpenDocument
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class CocktailAddEditFragment : Fragment(R.layout.fragment_cocktail_add_edit) {

    private lateinit var binding: FragmentCocktailAddEditBinding
    private val args: CocktailAddEditFragmentArgs by navArgs()
    private val adapter = IngredientItemAdapter()
    private val getContent =
        registerForActivityResult(CustomOpenDocument(), this::getContentCallback)

    @Inject
    lateinit var viewModelFactory: CocktailSettingsViewModelFactory
    private val viewModel: CocktailSettingsViewModel by viewModels {
        CocktailSettingsViewModel.provideFactory(viewModelFactory, args.cocktail)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCocktailAddEditBinding.bind(view)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vModel = viewModel
            ingredientList.adapter = adapter

            cocktailName.editText?.setOnFocusChangeListener { _, _ ->
                viewModel.requiredCocktailName(cocktailName)
            }
        }

        viewModelApply()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.settingsEvent.collect {
                when (it) {
                    CocktailAddEditEvent.EditSuccess -> {
                        // TODO: display message
                        findNavController().popBackStack()
                    }
                    CocktailAddEditEvent.EmptyQuantity -> {
                        Toast.makeText(context, R.string.fill_all_fields, Toast.LENGTH_SHORT).show()
                    }
                    CocktailAddEditEvent.UpdateImage -> {
                        getContent.launch(arrayOf("image/*"))
                    }
                    CocktailAddEditEvent.CreateSuccess -> {
                        // TODO: display message
                        findNavController().popBackStack()
                    }
                    CocktailAddEditEvent.SQLInsertError -> {
                        Toast.makeText(context, R.string.check_inserted_value, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun viewModelApply() {
        viewModel.apply {
            cocktail.observe(viewLifecycleOwner) {
                Glide.with(requireContext()).load(it.cocktailUri).centerCrop()
                    .placeholder(R.drawable.ic_insert_photo).into(binding.bigCocktailImg)

            }

            quantities.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }

            ingredients.observe(viewLifecycleOwner) {
                adapter.setIngredients(it)
            }

        }
    }

    private fun getContentCallback(uri: Uri?) {
        uri?.let {
            requireContext().contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            viewModel.setCocktailUri(it)
        }
    }
}