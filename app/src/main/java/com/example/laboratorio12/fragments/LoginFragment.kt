package com.example.laboratorio12.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.laboratorio12.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.findNavController
import com.example.laboratorio12.databinding.FragmentLoginBinding
import kotlinx.coroutines.flow.first

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var inputCorreo: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var buttonLogin: Button

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


        buttonLogin = view.findViewById(R.id.button_iniciar_sesion)
        inputCorreo = view.findViewById(R.id.input_correo)
        inputPassword = view.findViewById(R.id.input_contraseña)

        CoroutineScope(Dispatchers.Main).launch {
            if(getValueFromCorreo() == "alv21808@uvg.edu.gt")
            {
                requireView().findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                )
            }
        }

        //setListeners()
    }

    private suspend fun getValueFromCorreo() : String? {
        val dataStoreKey = stringPreferencesKey("correo")
        val preferences = context?.dataStore?.data?.first()

        return preferences?.get(dataStoreKey) ?: "null"
    }

}