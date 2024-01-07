package com.example.simbirsoft.notes.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.example.simbirsoft.databinding.FragmentNotesBinding
import com.example.simbirsoft.notes.domain.models.TimetableItem
import com.example.simbirsoft.notes.ui.adapter.HourTimetableAdapter
import com.example.simbirsoft.notes.ui.models.NotesScreenState
import com.example.simbirsoft.notes.ui.view_model.NotesViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by viewModel()

    private var onNoteClickListener: ((TimetableItem) -> Unit)? = null

    private var adapter: HourTimetableAdapter? = null

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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.screenState.collect {
                    render(it)
                }
            }
        }

        setClickListeners()
        getMonthNotes()
        setRecyclerViewAdapter()
        getFirstSelectedDateNotes()
    }


    private fun setRecyclerViewAdapter() {
        adapter = HourTimetableAdapter(
            onNoteClickListener ?: throw NullPointerException("onNoteClickListener is not initialized")
        )
        binding.rvTimetable.adapter = adapter
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
            }
        })
        binding.calendar.setOnForwardPageChangeListener(object : OnCalendarPageChangeListener {
            override fun onChange() {
                getMonthNotes()
            }
        })
        binding.calendar.setOnPreviousPageChangeListener(object : OnCalendarPageChangeListener {
            override fun onChange() {
                getMonthNotes()
            }
        })
        onNoteClickListener = {
            Log.d("NOTE_ID", it.id.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun render(state: NotesScreenState) {
        when (state) {
            is NotesScreenState.CalendarContent -> {
                Log.d("EVENT_NOTE1", state.eventList.toString())
                binding.calendar.setEvents(
                    state.eventList
                )
            }

            is NotesScreenState.TimetableContent -> {
                Log.d("EVENT_NOTE1", state.hourTimetableList.toString())
                adapter?.submitList(state.hourTimetableList)
            }

            is NotesScreenState.Error -> {

            }

            is NotesScreenState.Loading -> {

            }
        }
    }
}