package com.example.laboratorio12.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.laboratorio12.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.laboratorio12.databinding.FragmentLoginBinding
import com.example.laboratorio12.observables.SessionViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: SessionViewModel by viewModels()

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.inflate(layoutInflater)

        CoroutineScope(Dispatchers.Main).launch {
            if(getValueFromCorreo() == "alv21808@uvg.edu.gt")
            {
                requireView().findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                )
            }
        }

        setListeners()

    }

    private fun setListeners() {
        binding.buttonIniciarSesion.setOnClickListener{
            viewModel.viewModelScope.launch {
                binding.buttonIniciarSesion.isVisible = false
                binding.progressSecondFragment.isVisible = true
                delay(2000L)
                binding.buttonIniciarSesion.isVisible = true
            }
            verificarLogin()

        }
    }

    private fun verificarLogin() {
        val correo = binding.inputCorreo.editText!!.text.toString()
        val password = binding.inputContrasena.editText!!.text.toString()
        if(correo == password && correo.isNotEmpty() && password.isNotEmpty() && correo == "alv21808@uvg.edu.gt")
        {
            CoroutineScope(Dispatchers.IO).launch {
                saveCorreoLogin(
                    password = password
                )
                CoroutineScope(Dispatchers.Main).launch {
                    binding.inputCorreo.editText!!.text.clear()
                    binding.inputContrasena.editText!!.text.clear()
                    //viewModel.triggerStateFlow()
                    requireView().findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    )
                }
            }
        }
        else
        {
            binding.inputCorreo.editText!!.text.clear()
            binding.inputContrasena.editText!!.text.clear()

            Toast.makeText(
                context,
                getString(R.string.ingreso_invalido),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private suspend fun saveCorreoLogin(password: String) {
        val dataStoreKey = stringPreferencesKey("correo")
        context?.dataStore?.edit { settings ->
            settings[dataStoreKey] = password
        }
    }

    private suspend fun getValueFromCorreo() : String? {
        val dataStoreKey = stringPreferencesKey("correo")
        val preferences = context?.dataStore?.data?.first()

        return preferences?.get(dataStoreKey) ?: "null"
    }

}