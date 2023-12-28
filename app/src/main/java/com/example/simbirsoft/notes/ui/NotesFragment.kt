package com.example.simbirsoft.notes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.simbirsoft.databinding.FragmentNotesBinding
import com.example.simbirsoft.notes.ui.models.NotesScreenState
import com.example.simbirsoft.notes.ui.view_model.NotesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.screenState.observe(viewLifecycleOwner) {
            render(it)
        }
        viewModel.getNoteList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun render(state: NotesScreenState) {
        when (state) {
            is NotesScreenState.Content -> {

            }

            is NotesScreenState.Error -> {

            }

            is NotesScreenState.Loading -> {

            }
        }
    }
}