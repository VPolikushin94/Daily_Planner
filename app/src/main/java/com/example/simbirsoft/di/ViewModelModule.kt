package com.example.simbirsoft.di

import com.example.simbirsoft.note_creator.ui.view_model.NoteCreatorViewModel
import com.example.simbirsoft.note_details.ui.view_model.NoteDetailsViewModel
import com.example.simbirsoft.notes.ui.view_model.NotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        NotesViewModel(
            notesInteractor = get()
        )
    }

    viewModel {
        NoteDetailsViewModel(
            noteDetailsInteractor = get()
        )
    }

    viewModel {
        NoteCreatorViewModel()
    }
}