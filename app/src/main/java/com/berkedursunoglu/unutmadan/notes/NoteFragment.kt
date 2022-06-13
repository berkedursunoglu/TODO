package com.berkedursunoglu.unutmadan.notes

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.berkedursunoglu.a.R
import com.berkedursunoglu.a.databinding.FragmentNoteBinding
import com.berkedursunoglu.unutmadan.model.NoteModel


class NoteFragment : Fragment() {

    private lateinit var dataBinding: FragmentNoteBinding
    private lateinit var viewModel: NoteFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_note, container, false)
        val activity =
            return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.noteRecyclerView.layoutManager = GridLayoutManager(view.context, 2)
        viewModel = ViewModelProvider(this)[NoteFragmentViewModel::class.java]
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(dataBinding.noteToolbar)
        getData()
        dataBinding.noteAdd.setOnClickListener {
            val action = NoteFragmentDirections.noteFragtoEditFrag(howToCame = "new")
            view.findNavController().navigate(action)
        }
    }

    private fun getData() {
        viewModel.getDataFromRoom(dataBinding.root.context)
        viewModel.arrayListNote.observe(viewLifecycleOwner) {
            var rv = NoteRecyclerView(it as ArrayList<NoteModel>)
            dataBinding.noteRecyclerView.adapter = rv
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_note -> {
                deleteNote()
                true
            }
            else -> return false
        }
    }

    private fun deleteNote() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Uyarı")
        alertDialog.setMessage("Tüm notlarınız silinecektir. Onaylıyor musunuz?")
        alertDialog.setPositiveButton("Tamam") { dialog, which ->
            viewModel.deleteAll(requireContext())
            getData()
        }
        alertDialog.setNegativeButton("Hayır") { dialog, which ->

        }
        alertDialog.show()

    }


}

