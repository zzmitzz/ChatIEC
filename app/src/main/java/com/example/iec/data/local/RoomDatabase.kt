package com.example.iec.data.local

import androidx.room.Database
import androidx.room.RoomDatabase



@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDAO() : NoteDao
}