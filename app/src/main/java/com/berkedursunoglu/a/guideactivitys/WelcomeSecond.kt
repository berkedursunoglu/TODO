package com.berkedursunoglu.a.guideactivitys

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.berkedursunoglu.a.R
import com.berkedursunoglu.a.databinding.FragmentWelcomeSecondBinding


class WelcomeSecond : Fragment() {

    private lateinit var dataBinding: FragmentWelcomeSecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_welcome_second,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.imagebuttonthity.setOnClickListener {
            val action = WelcomeSecondDirections.actionWelcomeSecondToWelcomeThirt()
            requireView().findNavController().navigate(action)
        }
    }


}