package com.berkedursunoglu.a.notes

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.berkedursunoglu.a.R
import com.berkedursunoglu.a.databinding.FragmentEditBinding
import com.berkedursunoglu.a.model.NoteModel
import com.berkedursunoglu.a.services.NoteDatabase
import kotlinx.coroutines.coroutineScope


class EditFragment : Fragment() {

    private lateinit var dataBinding: FragmentEditBinding
    private lateinit var viewModel: EditFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit,container,false)
        viewModel = ViewModelProvider(this)[EditFragmentViewModel::class.java]
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (safeArgs() == "edit"){
            getDataUUID(uuidArgs())
        }

        dataBinding.addButton.setOnClickListener {
            if (safeArgs() == "new"){
                insertData()
            }else if(safeArgs() == "edit"){
                updateData(uuidArgs())
            }
        }

    }

    private fun safeArgs():String{
        val args: EditFragmentArgs by navArgs()
        return args.howToCame
    }

    private fun uuidArgs():Int{
        val args :EditFragmentArgs by navArgs()
        return args.uuid
    }

    private fun insertData(){
        var edittext = dataBinding.noteEditText.text.toString()
        if(edittext != ""){
            val model = NoteModel(edittext)
            viewModel.insertData(dataBinding.root.context,model)
            dataBinding.noteEditText.text.clear()
            val action = EditFragmentDirections.editFragtoNoteFrag()
            requireView().findNavController().navigate(action)
        }
    }

    private fun getDataUUID(uuid:Int){
        viewModel.getDatawithUUID(requireContext(),uuid)
        viewModel.editData.observe(viewLifecycleOwner){
            dataBinding.noteEditText.setText(it)
        }
    }

    private fun updateData(uuid: Int){
        var editText = dataBinding.noteEditText.text.toString()
        if (editText != ""){
            viewModel.editData(requireContext(),uuid,editText)
            var action = EditFragmentDirections.editFragtoNoteFrag()
            requireView().findNavController().navigate(action)
        }


    }

}