package com.example.simbirsoft.di

import androidx.room.Room
import com.example.simbirsoft.core.data.db.AppDatabase
import com.example.simbirsoft.core.data.db.mapper.NoteDbMapper
import com.example.simbirsoft.core.data.mock_data.MockNotes
import com.example.simbirsoft.core.data.mapper.NoteJsonMapper
import com.example.simbirsoft.core.data.mapper.NoteDtoMapper
import com.example.simbirsoft.core.data.network.api.NetworkClient
import com.example.simbirsoft.core.data.network.api.NoteService
import com.example.simbirsoft.core.data.network.impl.NetworkClientImpl
import com.example.simbirsoft.core.data.network.impl.NoteServiceImpl
import com.example.simbirsoft.note_details.data.repository.NoteDetailsRepositoryImpl
import com.example.simbirsoft.note_details.domain.api.NoteDetailsRepository
import com.example.simbirsoft.notes.data.repository.NotesRepositoryImpl
import com.example.simbirsoft.notes.domain.api.NotesRepository
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    factory { Gson() }

    factory { MockNotes() }

    factory {
        NoteJsonMapper(
            gson = get()
        )
    }

    factory { NoteDtoMapper() }

    factory { NoteDbMapper() }

    single<NoteService> {
        NoteServiceImpl(
            mockNotes = get(),
            noteJsonMapper = get()
        )
    }

    single<NetworkClient> {
        NetworkClientImpl(
            noteService = get()
        )
    }

    single<NotesRepository> {
        NotesRepositoryImpl(
            networkClient = get(),
            noteDtoMapper = get(),
            appDatabase = get(),
            noteDbMapper = get()
        )
    }

    single<NoteDetailsRepository> {
        NoteDetailsRepositoryImpl(
            appDatabase = get(),
            noteDbMapper = get()
        )
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db").build()
    }

}