package com.example.simbirsoft.notes.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.example.simbirsoft.databinding.FragmentNotesBinding
import com.example.simbirsoft.notes.ui.models.NotesScreenState
import com.example.simbirsoft.notes.ui.view_model.NotesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.screenState.observe(viewLifecycleOwner) {
            render(it)
        }

        getMonthNotes()
        getFirstSelectedDateNotes()
        setClickListeners()
    }

    private fun getMonthNotes() {
        val currentPageDate = binding.calendar.currentPageDate
        viewModel.getMonthNoteList(currentPageDate)
    }

    private fun getFirstSelectedDateNotes() {
        val firstSelectedDate = binding.calendar.firstSelectedDate
        viewModel.getDayNoteList(firstSelectedDate)
    }

    private fun setClickListeners() {
        binding.calendar.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                val selectedDate = eventDay.calendar
                viewModel.getDayNoteList(selectedDate)
                Log.d("EVENTS_CAL", selectedDate.get(Calendar.DAY_OF_MONTH).toString())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun render(state: NotesScreenState) {
        when (state) {
            is NotesScreenState.CalendarContent -> {
                binding.calendar.setEvents(
                    state.eventList
                )
            }

            is NotesScreenState.TimetableContent -> {

            }

            is NotesScreenState.Error -> {

            }

            is NotesScreenState.Loading -> {

            }
        }
    }
}