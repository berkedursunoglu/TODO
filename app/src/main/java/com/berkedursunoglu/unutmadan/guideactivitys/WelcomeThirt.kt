package com.berkedursunoglu.unutmadan.guideactivitys

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.berkedursunoglu.a.R
import com.berkedursunoglu.a.databinding.FragmentWelcomeThirtBinding
import com.berkedursunoglu.unutmadan.main.MainActivity


class WelcomeThirt : Fragment() {

    private lateinit var dataBinding: FragmentWelcomeThirtBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_welcome_thirt,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.thirtyimagebutton.setOnClickListener {
            startActivity(Intent(requireContext(),MainActivity::class.java))
            requireActivity().finish()
            val shared = requireContext().getSharedPreferences("welcomepage",0)
            val edit = shared.edit()
            edit.putInt("notshow",1).apply()
        }
    }



}