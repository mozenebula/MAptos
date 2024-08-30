package com.qstack.maptos.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.qstack.maptos.MainActivity
import com.qstack.maptos.R
import com.qstack.maptos.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val bottomDialog : BottomDialogFragment = BottomDialogFragment()
        binding.importWallet.setOnClickListener{
            bottomDialog.show(supportFragmentManager, bottomDialog.tag)
        }

        val loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.createWallet.setOnClickListener{
            loginViewModel.createAccount(binding.root.context)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }
}