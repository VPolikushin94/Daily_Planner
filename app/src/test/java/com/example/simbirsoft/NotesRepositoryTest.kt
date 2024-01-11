package com.example.simbirsoft

import com.example.simbirsoft.core.data.db.AppDatabase
import com.example.simbirsoft.core.data.db.dao.NoteDao
import com.example.simbirsoft.core.data.db.entity.NoteEntity
import com.example.simbirsoft.core.data.db.mapper.NoteDbMapper
import com.example.simbirsoft.core.data.mapper.NoteDtoMapper
import com.example.simbirsoft.core.data.network.api.NetworkClient
import com.example.simbirsoft.notes.data.repository.NotesRepositoryImpl
import com.example.simbirsoft.notes.domain.models.HourTimetableItem
import com.example.simbirsoft.util.toTimestamp
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Calendar

class NotesRepositoryTest {

    @MockK
    lateinit var networkClient: NetworkClient

    @MockK
    lateinit var noteDtoMapper: NoteDtoMapper

    @MockK
    lateinit var appDatabase: AppDatabase

    @MockK
    lateinit var noteDbMapper: NoteDbMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getDayNoteList() = runBlocking {
        val noteDao: NoteDao = mockk()
        val noteEntityList: List<NoteEntity> = mockk()
        val hourTimetableItemList: List<HourTimetableItem> = mockk()
        val calendar: Calendar = Calendar.getInstance()
        val date = calendar.timeInMillis.toTimestamp()

        every { appDatabase.noteDao() } returns noteDao

        val notesRepository = NotesRepositoryImpl(
            networkClient,
            noteDtoMapper,
            appDatabase,
            noteDbMapper
        )

        coEvery { noteDao.getDayNotes(date) } returns noteEntityList
        coEvery { noteDbMapper.map(noteEntityList, calendar) } returns hourTimetableItemList

        assertEquals(notesRepository.getDayNoteList(calendar), hourTimetableItemList)
    }
}
