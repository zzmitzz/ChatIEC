package com.example.iec.data.repository

import com.example.data.IECNote


interface NoteRepository {
    suspend fun getNoteOffline(userID: String): List<IECNote>
    suspend fun syncNote(userID: String): Unit
    suspend fun updateNote(noteID: String, note: IECNote): Boolean
    suspend fun deleteNote(noteID: String): Boolean
    suspend fun addNote(note: IECNote): Boolean
}