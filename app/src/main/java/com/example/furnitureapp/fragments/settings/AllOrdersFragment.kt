package com.example.furnitureapp.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.furnitureapp.adapters.AllOrdersAdapter
import com.example.furnitureapp.databinding.FragmentOrdersBinding
import com.example.furnitureapp.util.Resource
import com.example.furnitureapp.viewmodel.AllOrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AllOrdersFragment : Fragment() {

    private lateinit var binding: FragmentOrdersBinding
    val viewModel by viewModels<AllOrdersViewModel>()
    val ordersAdapter by lazy { AllOrdersAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOrderRv()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allOrders.collectLatest {
                    when(it) {
                        is Resource.Loading -> {
                            binding.progressbarAllOrders.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.progressbarAllOrders.visibility = View.GONE
                            ordersAdapter.differ.submitList(it.data)
                            if (it.data.isNullOrEmpty()) {
                                binding.tvEmptyOrders.visibility = View.VISIBLE
                            }
                        }
                        is Resource.Error -> {
                            Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_LONG).show()
                            binding.progressbarAllOrders.visibility = View.GONE

                        }
                        else -> Unit
                    }
                }
            }
        }
        ordersAdapter.onClick = {
            val action = AllOrdersFragmentDirections.actionAllOrdersFragmentToOrderDetailFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun setupOrderRv() {
        binding.rvAllOrders.apply {
            adapter = ordersAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }
}