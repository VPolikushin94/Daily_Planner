package com.example.simbirsoft.di

import com.example.simbirsoft.notes.ui.view_model.NotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        NotesViewModel(
            notesInteractor = get()
        )
    }
}