package com.example.simbirsoft.notes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.simbirsoft.notes.data.db.dao.NoteDao
import com.example.simbirsoft.notes.data.db.entity.NoteEntity

@Database(version = 1, entities = [NoteEntity::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
}