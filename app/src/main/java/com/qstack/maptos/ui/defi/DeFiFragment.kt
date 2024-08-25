package com.qstack.maptos.ui.defi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.qstack.maptos.databinding.FragmentDefiBinding

class DeFiFragment : Fragment() {
    private var _binding: FragmentDefiBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val deFiViewModel = ViewModelProvider(this)[DeFiViewModel::class.java]
        _binding = FragmentDefiBinding.inflate(inflater, container, false)

        val textView: TextView = binding.textDeFi

        deFiViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null;
    }
}