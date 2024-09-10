package com.qstack.maptos.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.qstack.maptos.databinding.FragmentHomeBinding
import com.qstack.maptos.ui.home.tabs.HomeNFTFragment
import com.qstack.maptos.ui.home.tabs.HomeTokenFragment
import android.content.Context
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.qstack.maptos.R
import kotlin.math.abs

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

        val pageAdapter = MyPagerAdapter(this)
        binding.viewpager.adapter = pageAdapter

        TabLayoutMediator(binding.tabs, binding.viewpager) {
                tab, position ->
            tab.text = when(position) {
                0 -> "Token"
                else -> "NFT"
            }
        }.attach()


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class MyPagerAdapter(fragmentActivity: HomeFragment) : FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return 2// 页数
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> HomeTokenFragment()
                1 -> HomeNFTFragment()
                else -> throw IllegalStateException("Unexpected position $position")
            }
        }
    }


}