package com.example.simbirsoft.note_creator.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simbirsoft.core.domain.models.Note
import com.example.simbirsoft.note_creator.domain.api.NoteCreatorInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class NoteCreatorViewModel(
    private val noteCreatorInteractor: NoteCreatorInteractor
) : ViewModel() {

    private val _isSaved = MutableSharedFlow<Boolean>()
    val isSaved: SharedFlow<Boolean> = _isSaved

    val startTime: Calendar = Calendar.getInstance()
    val endTime: Calendar = Calendar.getInstance()

    fun saveNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            val isSaved = noteCreatorInteractor.saveNote(note)
            _isSaved.emit(isSaved)
        }
    }
}