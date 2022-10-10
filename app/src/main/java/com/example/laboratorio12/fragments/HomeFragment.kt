package com.example.laboratorio12.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.laboratorio12.R
import com.example.laboratorio12.databinding.FragmentHomeBinding
import com.example.laboratorio12.observables.HomeViewModel
import com.example.laboratorio12.observables.SessionViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val sessionViewModel: SessionViewModel by viewModels()
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setObservables()
    }

    private fun setListeners() {

    }


    private fun setObservables() {
        /*lifecycleScope.launchWhenStarted {
            sessionViewModel.stateFlow.collectLatest { newState ->
                if(!newState){
                    requireView().findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                    )
                }
            }
        }*/
        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest { state ->
                handleState(state)
            }
        }
    }

    private fun handleState(state: HomeViewModel.HomeState){
        when(state) {
            is HomeViewModel.HomeState.Default -> {
                viewModel.viewModelScope.launch {
                    binding.progressHomeFragment.isVisible = true
                    delay(2000L)
                    binding.progressHomeFragment.isVisible = false
                    binding.iconHomeFragment.isVisible = true
                    binding.textViewHomeFragment.isVisible = true
                    binding.iconHomeFragment.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_default))
                    binding.textViewHomeFragment.text = state.messageDefault
                    binding.buttonDefaultHomeFragment.isEnabled = true
                    binding.buttonSuccessHomeFragment.isEnabled = false
                    binding.buttonFailureHomeFragment.isEnabled = false
                    binding.buttonEmptyHomeFragment.isEnabled = false
                }
            }
            is HomeViewModel.HomeState.Empty -> {
                viewModel.viewModelScope.launch {
                    binding.progressHomeFragment.isVisible = true
                    delay(2000L)
                    binding.progressHomeFragment.isVisible = false
                    binding.iconHomeFragment.isVisible = true
                    binding.textViewHomeFragment.isVisible = true
                    binding.iconHomeFragment.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_empty))
                    binding.textViewHomeFragment.text = state.messageEmpy
                    binding.buttonDefaultHomeFragment.isEnabled = false
                    binding.buttonSuccessHomeFragment.isEnabled = false
                    binding.buttonFailureHomeFragment.isEnabled = false
                    binding.buttonEmptyHomeFragment.isEnabled = true
                }
            }
            is HomeViewModel.HomeState.Failure -> {
                viewModel.viewModelScope.launch {
                    binding.progressHomeFragment.isVisible = true
                    delay(2000L)
                    binding.progressHomeFragment.isVisible = false
                    binding.iconHomeFragment.isVisible = true
                    binding.textViewHomeFragment.isVisible = true
                    binding.iconHomeFragment.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_failure))
                    binding.textViewHomeFragment.text = state.messageFailure
                    binding.buttonDefaultHomeFragment.isEnabled = false
                    binding.buttonSuccessHomeFragment.isEnabled = false
                    binding.buttonFailureHomeFragment.isEnabled = true
                    binding.buttonEmptyHomeFragment.isEnabled = false
                }
            }
            is HomeViewModel.HomeState.Started -> {
                binding.progressHomeFragment.isVisible = false
                binding.progressHomeFragment.isVisible = false
                binding.iconHomeFragment.isVisible = false
                binding.textViewHomeFragment.isVisible = false
                binding.iconHomeFragment.isVisible = false
                binding.textViewHomeFragment.isVisible = false
                binding.buttonDefaultHomeFragment.isEnabled = false
                binding.buttonSuccessHomeFragment.isEnabled = false
                binding.buttonFailureHomeFragment.isEnabled = false
                binding.buttonEmptyHomeFragment.isEnabled = false
            }
            is HomeViewModel.HomeState.Success -> {
                viewModel.viewModelScope.launch {
                    binding.progressHomeFragment.isVisible = true
                    delay(2000L)
                    binding.progressHomeFragment.isVisible = false
                    binding.iconHomeFragment.isVisible = true
                    binding.textViewHomeFragment.isVisible = true
                    binding.iconHomeFragment.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_success))
                    binding.textViewHomeFragment.text = state.messageSuccess
                    binding.buttonDefaultHomeFragment.isEnabled = true
                    binding.buttonSuccessHomeFragment.isEnabled = false
                    binding.buttonFailureHomeFragment.isEnabled = false
                    binding.buttonEmptyHomeFragment.isEnabled = false
                }
            }
        }
    }

}