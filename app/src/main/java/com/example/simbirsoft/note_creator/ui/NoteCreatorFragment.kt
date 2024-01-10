package com.example.simbirsoft.note_creator.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.simbirsoft.R
import com.example.simbirsoft.core.domain.models.Note
import com.example.simbirsoft.databinding.FragmentNoteCreatorBinding
import com.example.simbirsoft.note_creator.ui.models.SaveErrorType
import com.example.simbirsoft.note_creator.ui.models.SaveState
import com.example.simbirsoft.note_creator.ui.view_model.NoteCreatorViewModel
import com.example.simbirsoft.util.changeKeyboardVisibility
import com.example.simbirsoft.util.getFormatDate
import com.example.simbirsoft.util.getFormatTime
import kotlinx.coroutines.launch
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

        val selectedDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getSerializable(CALENDAR, Calendar::class.java)
                ?: Calendar.getInstance()
        } else {
            requireArguments().getSerializable(CALENDAR) as Calendar
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isSaved.collect {
                    showSaveState(it)
                }
            }
        }

        setSelectedDate(selectedDate)
        setClickListeners()
        setTouchListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showSaveState(state: SaveState) {
        when (state) {
            is SaveState.Success -> {
                showToast(R.string.note_saved)
                parentFragmentManager.popBackStack()
            }

            is SaveState.Error -> {
                showError(state.saveErrorType)
            }
        }
    }

    private fun showError(errorType: SaveErrorType) {
        when (errorType) {
            SaveErrorType.EMPTY_NAME -> {
                binding.etName.requestFocus()
                changeKeyboardVisibility(true, requireContext(), binding.etName)
                showToast(R.string.enter_name)
            }

            SaveErrorType.DB_ERROR -> showToast(R.string.note_save_error)

            SaveErrorType.SAME_DATE -> showToast(R.string.same_time_error)

            SaveErrorType.FINISH_BEFORE_START -> showToast(R.string.finish_before_start)
        }
    }

    private fun showToast(@StringRes stringId: Int) {
        Toast.makeText(requireContext(), stringId, Toast.LENGTH_SHORT).show()
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
        binding.btnCompleteDescription.setOnClickListener {
            changeKeyboardVisibility(false, requireContext(), binding.etDescription)
            binding.etDescription.clearFocus()
            binding.etDescription.setText(
                binding.etDescription.text.trim()
            )

        }
        binding.btnSaveNote.setOnClickListener {
            saveNote()
        }
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun saveNote() {
        val note = Note(
            0,
            viewModel.startTime,
            viewModel.endTime,
            binding.etName.text.toString(),
            binding.etDescription.text.toString()
        )
        viewModel.saveNote(note)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListeners() {
        binding.clScreen.setOnTouchListener { _, _ ->
            changeKeyboardVisibility(false, requireContext(), binding.etDescription)
            binding.etDescription.setText(
                binding.etDescription.text.trim()
            )
            binding.etDescription.clearFocus()
            binding.etName.clearFocus()
            false
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
                Log.d("NOTE_TIME", hourOfDay.toString())
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