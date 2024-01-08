package com.example.simbirsoft.note_creator.ui.view_model

import androidx.lifecycle.ViewModel
import java.util.Calendar

class NoteCreatorViewModel : ViewModel() {

    val startTime: Calendar = Calendar.getInstance()
    val endTime: Calendar = Calendar.getInstance()
}