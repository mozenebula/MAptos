package com.qstack.maptos.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.qstack.maptos.databinding.FragmentHomeBinding
import com.qstack.maptos.aptos.KeystoreHelper
import com.qstack.maptos.aptos.WalletManager
import com.qstack.maptos.aptos.room.Wallet
import com.qstack.maptos.aptos.room.WalletRepository
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.buttonHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        binding.buttonHome.setOnClickListener{
            val walletRepository = WalletRepository(binding.root.context)
            val mnemonic = WalletManager.generateMnemonic()
            val privateKey = WalletManager.generatePrivateKey(mnemonic)
            // 创建一个 Wallet 对象
            val wallet = Wallet(
                walletName = "钱包A",
                accountName = "Account 1",
                address = WalletManager.getAddress(privateKey),
                privateKey = KeystoreHelper.encryptPrivateKey(privateKey.toString()),
                isBackedUp = false,
                mnemonic = KeystoreHelper.encryptPrivateKey(mnemonic),
                network = "Aptos"
            )

            // 插入钱包到数据库
            lifecycleScope.launch {
                walletRepository.insertWallet(wallet)
            }


            Log.e("TEST","插入数据：" + wallet)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}