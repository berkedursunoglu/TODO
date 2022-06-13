package com.berkedursunoglu.a.guideactivitys

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.berkedursunoglu.a.R
import com.berkedursunoglu.a.databinding.FragmentWelcomeFirstBinding
import com.berkedursunoglu.a.main.MainActivity


class WelcomeFirst : Fragment() {

    private lateinit var dataBinding: FragmentWelcomeFirstBinding
    private lateinit var anim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_welcome_first,container,false)
        val shared = requireContext().getSharedPreferences("welcomepage",0)
        val notshow = shared.getInt("notshow",0)
        if (notshow == 1){
            startActivity(Intent(requireContext(),MainActivity::class.java))
            requireActivity().finish()
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        anim = AnimationUtils.loadAnimation(requireContext(),R.anim.anim)

        dataBinding.welcometextfirstdesc.startAnimation(anim)
        dataBinding.welcometextfirst.startAnimation(anim)
        dataBinding.welcomeimagefirst.startAnimation(anim)
        dataBinding.pagefirst.startAnimation(anim)
        dataBinding.imagebuttonfirst.startAnimation(anim)
        dataBinding.imagebuttonfirst.setOnClickListener {
            val action = WelcomeFirstDirections.actionWelcomeFirstToWelcomeSecond()
            requireView().findNavController().navigate(action)
        }

    }

}