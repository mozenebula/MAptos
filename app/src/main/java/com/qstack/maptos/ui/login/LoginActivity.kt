package com.qstack.maptos.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.FrameMetrics
import android.view.Window
import android.view.Window.OnFrameMetricsAvailableListener
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.qstack.maptos.MainActivity
import com.qstack.maptos.R
import com.qstack.maptos.aptos.data.WalletInfo
import com.qstack.maptos.aptos.room.WalletRepository
import com.qstack.maptos.databinding.ActivityLoginBinding
import com.qstack.maptos.databinding.FragmentImportBinding
import kotlinx.coroutines.launch

const val ACCOUNT_INFO = "ACCOUNT_INFO"

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        viewModel.checkWallet(binding.root.context)

//        window.addOnFrameMetricsAvailableListener(object : OnFrameMetricsAvailableListener {
//            override fun onFrameMetricsAvailable(p0: Window?, p1: FrameMetrics?, p2: Int) {
//               ;
//            }
//        }, null)

        //TODO("优化启动体验")
        viewModel.wallet.observe(this, Observer { wallet ->
            if(wallet != null) {
                val intent = Intent(binding.root.context, MainActivity::class.java)
                intent.putExtra(
                    ACCOUNT_INFO, WalletInfo(walletName = wallet.walletName,
                        accountName = wallet.accountName,
                        address = wallet.address,
                        isBackedUp = wallet.isBackedUp)
                )
                startActivity(intent)
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, LoginFragment())
                    .addToBackStack(null)
                    .commit()
            }
        })



        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(supportFragmentManager.backStackEntryCount > 1) {
                    supportFragmentManager.popBackStack()
                } else {
                    finish()
                }
            }
        })






        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}