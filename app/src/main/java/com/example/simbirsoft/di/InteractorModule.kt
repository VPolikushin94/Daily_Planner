package com.example.simbirsoft.di

import com.example.simbirsoft.note_details.domain.api.NoteDetailsInteractor
import com.example.simbirsoft.note_details.domain.impl.NoteDetailsInteractorImpl
import com.example.simbirsoft.notes.domain.api.NotesInteractor
import com.example.simbirsoft.notes.domain.impl.NotesInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    factory<NotesInteractor> {
        NotesInteractorImpl(
            notesRepository = get()
        )
    }

    factory<NoteDetailsInteractor> {
        NoteDetailsInteractorImpl(
            noteDetailsRepository = get()
        )
    }
}