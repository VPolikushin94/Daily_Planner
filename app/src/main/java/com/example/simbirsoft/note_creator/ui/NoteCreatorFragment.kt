package com.example.simbirsoft.note_creator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.simbirsoft.databinding.FragmentNoteCreatorBinding

class NoteCreatorFragment : Fragment() {

    private var _binding: FragmentNoteCreatorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNoteCreatorBinding.inflate(inflater, container, false)
        return binding.root
    }

}