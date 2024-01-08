package com.example.simbirsoft.note_details.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.simbirsoft.R
import com.example.simbirsoft.core.domain.models.Note
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.simbirsoft.databinding.FragmentNoteDetailBinding
import com.example.simbirsoft.note_details.ui.models.NoteDetailsScreenState
import com.example.simbirsoft.note_details.ui.view_model.NoteDetailsViewModel
import com.example.simbirsoft.util.getFormatDateTime
import kotlinx.coroutines.launch

class NoteDetailFragment : Fragment() {

    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.screenState.collect {
                    render(it)
                }
            }
        }

        val noteId = requireArguments().getInt(NOTE_ID)
        viewModel.getNote(noteId)

        setClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setClickListeners() {
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun render(state: NoteDetailsScreenState) {
        when (state) {
            is NoteDetailsScreenState.Loading -> {

            }

            is NoteDetailsScreenState.Content -> {
                setContent(state.note)
            }
        }
    }

    private fun setContent(note: Note) {
        binding.tvName.text = note.name

        val dateStart = note.calendarStart.time.getFormatDateTime()
        binding.tvDateStart.text = dateStart

        val dateFinish = note.calendarFinish.time.getFormatDateTime()
        binding.tvDateFinish.text = dateFinish

        binding.tvDescription.text = getString(R.string.description, note.description)
    }

    companion object {

        private const val NOTE_ID = "NOTE_ID"

        fun newInstance(id: Int): NoteDetailFragment {
            val args = bundleOf(NOTE_ID to id)
            
            val fragment = NoteDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}