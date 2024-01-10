package com.example.simbirsoft.note_creator.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simbirsoft.core.domain.models.Note
import com.example.simbirsoft.note_creator.domain.api.NoteCreatorInteractor
import com.example.simbirsoft.note_creator.ui.models.SaveErrorType
import com.example.simbirsoft.note_creator.ui.models.SaveState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class NoteCreatorViewModel(
    private val noteCreatorInteractor: NoteCreatorInteractor
) : ViewModel() {

    private val _isSaved = MutableSharedFlow<SaveState>()
    val isSaved: SharedFlow<SaveState> = _isSaved

    val startTime: Calendar = Calendar.getInstance()
    val endTime: Calendar = Calendar.getInstance()

    fun saveNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            if (note.name.isEmpty()) {
                _isSaved.emit(SaveState.Error(SaveErrorType.EMPTY_NAME))
                return@launch
            }

            if (note.calendarStart.time == note.calendarFinish.time) {
                _isSaved.emit(SaveState.Error(SaveErrorType.SAME_DATE))
                return@launch
            }

            if (note.calendarFinish.before(note.calendarStart)) {
                _isSaved.emit(SaveState.Error(SaveErrorType.FINISH_BEFORE_START))
                return@launch
            }

            val isSaved = noteCreatorInteractor.saveNote(note)
            if (isSaved) {
                _isSaved.emit(SaveState.Success)
            } else {
                _isSaved.emit(SaveState.Error(SaveErrorType.DB_ERROR))
            }

        }
    }
}