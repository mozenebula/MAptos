package com.qstack.maptos.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.qstack.maptos.databinding.FragmentImportBinding


private const val TYPE = "TYPE"
const val IMPORT_BY_MNEMONICS = "IMPORT_BY_MNEMONICS"
const val IMPORT_BY_PRIVATE_KEY = "IMPORT_BY_PRIVATE_KEY"

class ImportFragment : Fragment() {
    private  var _binding : FragmentImportBinding? = null

    private val binding get() = _binding!!
    // TODO: Rename and change types of parameters
    private var type: String? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString(TYPE)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImportBinding.inflate(inflater, container, false)
        if(type == IMPORT_BY_PRIVATE_KEY) {
            binding.importWallet.hint = "Enter the private key"
            binding.actionBarTitle.text = "Import Private Key"
        }
        binding.back.setOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.importWallet.requestFocus()
        binding.importWallet.postDelayed({
            val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(binding.importWallet, InputMethodManager.SHOW_IMPLICIT)
        }, 100)
    }

    companion object {
        @JvmStatic
        fun newInstance(type: String) =
            ImportFragment().apply {
                arguments = Bundle().apply {
                    putString(TYPE, type)
                }
            }
    }
}