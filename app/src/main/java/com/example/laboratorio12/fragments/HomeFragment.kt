package com.example.laboratorio12.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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

    private val sessionViewModel: SessionViewModel by activityViewModels()
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
        binding.buttonMantenerSesionHomeFragment.setOnClickListener {
            sessionViewModel.mantenerSesion()
        }
        binding.buttonCerrarSesionHomeFragment.setOnClickListener {
            sessionViewModel.cerrarSesion()
        }

        binding.buttonDefaultHomeFragment.setOnClickListener {
            viewModel.doDefault()
        }
        binding.buttonSuccessHomeFragment.setOnClickListener {
            viewModel.doSuccess()
        }
        binding.buttonFailureHomeFragment.setOnClickListener {
            viewModel.doFailure()
        }
        binding.buttonEmptyHomeFragment.setOnClickListener {
            viewModel.doEmpty()
        }
    }


    private fun setObservables() {
        lifecycleScope.launchWhenStarted {
            sessionViewModel.stateFlow.collectLatest { newState ->
                if(!newState){
                    requireView().findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                    )
                }
            }
        }
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
                    binding.apply {
                        iconHomeFragment.isVisible = false
                        textViewHomeFragment.isVisible = false
                        progressHomeFragment.isVisible = true
                        buttonDefaultHomeFragment.isEnabled = true
                        buttonSuccessHomeFragment.isEnabled = false
                        buttonFailureHomeFragment.isEnabled = false
                        buttonEmptyHomeFragment.isEnabled = false
                        delay(2000L)
                        progressHomeFragment.isVisible = false
                        iconHomeFragment.isVisible = true
                        textViewHomeFragment.isVisible = true
                        iconHomeFragment.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_default))
                        textViewHomeFragment.text = state.messageDefault
                        buttonDefaultHomeFragment.isEnabled = true
                        buttonSuccessHomeFragment.isEnabled = true
                        buttonFailureHomeFragment.isEnabled = true
                        buttonEmptyHomeFragment.isEnabled = true
                    }
                }
            }
            is HomeViewModel.HomeState.Empty -> {
                viewModel.viewModelScope.launch {
                    binding.apply {
                        iconHomeFragment.isVisible = false
                        textViewHomeFragment.isVisible = false
                        progressHomeFragment.isVisible = true
                        buttonDefaultHomeFragment.isEnabled = false
                        buttonSuccessHomeFragment.isEnabled = false
                        buttonFailureHomeFragment.isEnabled = false
                        buttonEmptyHomeFragment.isEnabled = true
                        delay(2000L)
                        progressHomeFragment.isVisible = false
                        iconHomeFragment.isVisible = true
                        textViewHomeFragment.isVisible = true
                        iconHomeFragment.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_empty))
                        textViewHomeFragment.text = state.messageEmpty
                        buttonDefaultHomeFragment.isEnabled = true
                        buttonSuccessHomeFragment.isEnabled = true
                        buttonFailureHomeFragment.isEnabled = true
                        buttonEmptyHomeFragment.isEnabled = true
                    }
                }
            }
            is HomeViewModel.HomeState.Failure -> {
                viewModel.viewModelScope.launch {
                    binding.apply {
                        iconHomeFragment.isVisible = false
                        textViewHomeFragment.isVisible = false
                        progressHomeFragment.isVisible = true
                        buttonDefaultHomeFragment.isEnabled = false
                        buttonSuccessHomeFragment.isEnabled = false
                        buttonFailureHomeFragment.isEnabled = true
                        buttonEmptyHomeFragment.isEnabled = false
                        delay(2000L)
                        progressHomeFragment.isVisible = false
                        iconHomeFragment.isVisible = true
                        textViewHomeFragment.isVisible = true
                        iconHomeFragment.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_failure))
                        textViewHomeFragment.text = state.messageFailure
                        buttonDefaultHomeFragment.isEnabled = true
                        buttonSuccessHomeFragment.isEnabled = true
                        buttonFailureHomeFragment.isEnabled = true
                        buttonEmptyHomeFragment.isEnabled = true
                    }
                }
            }
            is HomeViewModel.HomeState.Started -> {
                binding.progressHomeFragment.isVisible = false
                binding.progressHomeFragment.isVisible = false
                binding.iconHomeFragment.isVisible = false
                binding.textViewHomeFragment.isVisible = false
                binding.iconHomeFragment.isVisible = false
                binding.textViewHomeFragment.isVisible = false
                binding.buttonDefaultHomeFragment.isEnabled = true
                binding.buttonSuccessHomeFragment.isEnabled = true
                binding.buttonFailureHomeFragment.isEnabled = true
                binding.buttonEmptyHomeFragment.isEnabled = true
            }
            is HomeViewModel.HomeState.Success -> {
                viewModel.viewModelScope.launch {
                    binding.apply {
                        iconHomeFragment.isVisible = false
                        textViewHomeFragment.isVisible = false
                        progressHomeFragment.isVisible = true
                        buttonDefaultHomeFragment.isEnabled = false
                        buttonSuccessHomeFragment.isEnabled = true
                        buttonFailureHomeFragment.isEnabled = false
                        buttonEmptyHomeFragment.isEnabled = false
                        delay(2000L)
                        progressHomeFragment.isVisible = false
                        iconHomeFragment.isVisible = true
                        textViewHomeFragment.isVisible = true
                        iconHomeFragment.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_success))
                        textViewHomeFragment.text = state.messageSuccess
                        buttonDefaultHomeFragment.isEnabled = true
                        buttonSuccessHomeFragment.isEnabled = true
                        buttonFailureHomeFragment.isEnabled = true
                        buttonEmptyHomeFragment.isEnabled = true
                    }
                }
            }
        }
    }

}