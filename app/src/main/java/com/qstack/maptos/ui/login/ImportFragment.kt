package com.qstack.maptos.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qstack.maptos.MainActivity
import com.qstack.maptos.R
import com.qstack.maptos.aptos.data.WalletInfo
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

        val viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]

        viewModel.checkImport.observe(viewLifecycleOwner, Observer {
            if(viewModel.checkImport.value == true) {
                binding.confirm.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white))
                binding.confirmText.setTextColor(Color.BLACK)
                binding.confirm.isClickable = true
            } else {
                binding.confirm.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.import_wallet_button))
                binding.confirmText.setTextColor(ContextCompat.getColor(requireContext(), R.color.import_wallet_button_text))
                binding.confirm.isClickable = false
            }
        })

        viewModel.wallet.observe(viewLifecycleOwner, Observer { wallet ->
            val intent = Intent(requireContext(), MainActivity::class.java)
            if(wallet != null) {
                intent.putExtra(
                    ACCOUNT_INFO, WalletInfo(walletName = wallet.walletName,
                        accountName = wallet.accountName,
                        address = wallet.address,
                        isBackedUp = wallet.isBackedUp))
            }
            startActivity(intent)
        })

        binding.confirm.setOnClickListener{
            if(type == IMPORT_BY_MNEMONICS) {
                viewModel.createAccount(requireContext(), binding.importWallet.text.toString().trim())
            } else {
                viewModel.createAccountByPrivateKey(requireContext(), binding.importWallet.text.toString().trim())
            }
        }

        binding.importWallet.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val s = binding.importWallet.text.toString().trim()
                if(type == IMPORT_BY_MNEMONICS) {
                    viewModel.isValidMnemonics(s)
                } else {
                    viewModel.isValidPrivateKey(s)
                }
            }
        })

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