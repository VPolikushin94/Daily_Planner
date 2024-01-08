package com.example.simbirsoft.note_creator.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.simbirsoft.R
import com.example.simbirsoft.databinding.FragmentNoteCreatorBinding
import com.example.simbirsoft.note_creator.ui.view_model.NoteCreatorViewModel
import com.example.simbirsoft.util.getFormatDate
import com.example.simbirsoft.util.getFormatTime
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class NoteCreatorFragment : Fragment() {

    private var _binding: FragmentNoteCreatorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteCreatorViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNoteCreatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedDate = requireArguments().getSerializable(CALENDAR) as Calendar

        setSelectedDate(selectedDate)
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnPickDateStart.setOnClickListener {
            pickNewDate(
                viewModel.startTime,
                binding.btnPickDateStart
            )
        }
        binding.btnPickDateEnd.setOnClickListener {
            pickNewDate(
                viewModel.endTime,
                binding.btnPickDateEnd
            )
        }
        binding.btnPickTimeStart.setOnClickListener {
            pickNewTime(
                viewModel.startTime,
                binding.btnPickTimeStart
            )
        }
        binding.btnPickTimeEnd.setOnClickListener {
            pickNewTime(
                viewModel.endTime,
                binding.btnPickTimeEnd
            )
        }
    }

    private fun pickNewDate(calendar: Calendar, button: Button) {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.DatePickerStyle,
            null,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
        )
        datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            button.text = calendar.time.getFormatDate()
        }
        datePickerDialog.show()
    }

    private fun pickNewTime(calendar: Calendar, button: Button) {
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            R.style.DatePickerStyle,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                button.text = calendar.time.getFormatTime()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()

    }

    private fun setSelectedDate(selectedDate: Calendar) {
        viewModel.startTime.timeInMillis = selectedDate.timeInMillis
        viewModel.endTime.timeInMillis = selectedDate.timeInMillis
        val date = selectedDate.time.getFormatDate()
        val time = selectedDate.time.getFormatTime()
        binding.btnPickDateStart.text = date
        binding.btnPickTimeStart.text = time
        binding.btnPickDateEnd.text = date
        binding.btnPickTimeEnd.text = time
    }

    companion object {

        private const val CALENDAR = "CALENDAR"
        fun newInstance(calendar: Calendar): NoteCreatorFragment {
            val args = bundleOf(CALENDAR to calendar)

            val fragment = NoteCreatorFragment()
            fragment.arguments = args
            return fragment
        }
    }
}