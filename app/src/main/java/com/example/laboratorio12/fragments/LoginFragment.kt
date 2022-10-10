package com.example.laboratorio12.fragments

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.laboratorio12.databinding.FragmentLoginBinding
import com.example.laboratorio12.observables.SessionViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first

class LoginFragment : Fragment(R.layout.fragment_login) {
    // Se siguió utilizando el mismo método aprendido anteriormente, ya que al tratar de utilizar
    // binding, el programan no se tenía el comportamiento esperado
    private lateinit var inputCorreo: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var buttonLogin: Button
    private val viewModel: SessionViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonLogin = view.findViewById(R.id.button_iniciar_sesion)
        inputCorreo = view.findViewById(R.id.input_correo)
        inputPassword = view.findViewById(R.id.input_contrasena)
        progressBar = view.findViewById(R.id.progress_loginFragment)

        setListeners()

    }

    private fun setListeners() {
        buttonLogin.setOnClickListener{
            val correo = inputCorreo.editText!!.text.toString()
            val password = inputPassword.editText!!.text.toString()
            viewModel.viewModelScope.launch {
                buttonLogin.isVisible = false
                progressBar.isVisible = true
                delay(2000L)
                if(viewModel.triggerStateFlow(correo, password)){
                    requireView().findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    )
                }
                else {
                    progressBar.isVisible = false
                    buttonLogin.isVisible = true
                    inputCorreo.editText!!.text.clear()
                    inputPassword.editText!!.text.clear()
                    Toast.makeText(
                        context,
                        getString(R.string.ingreso_invalido),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

}