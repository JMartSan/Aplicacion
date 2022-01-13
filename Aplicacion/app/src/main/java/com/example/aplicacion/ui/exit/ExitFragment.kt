package com.example.aplicacion.ui.exit

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aplicacion.DosPantalla
import com.example.aplicacion.R
import com.google.firebase.auth.FirebaseAuth

class ExitFragment : Fragment() {

    companion object {
        fun newInstance() = ExitFragment()
    }

    private lateinit var viewModel: ExitViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_exit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(ExitViewModel::class.java)
        // TODO: Use the ViewModel

        logout()
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        Thread.sleep(100)
        val volverIntent = Intent(this.context, DosPantalla::class.java)
        startActivity(volverIntent)
    }




}