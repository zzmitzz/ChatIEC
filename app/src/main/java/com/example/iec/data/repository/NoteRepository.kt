package com.example.iec.data.repository

import com.example.data.IECNote
import com.example.iec.data.local.Note
import com.example.iec.data.local.NoteDao
import com.example.iec.data.local.toNote

import com.example.iec.data.remote.NoteRemote
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

interface NoteRepository {
    suspend fun getNoteOffline(userID: String): List<Note>
    suspend fun syncNote(userID: String): Unit
    suspend fun updateNote(noteID: String, note: IECNote): Boolean
    suspend fun deleteNote(noteID: String): Boolean
    suspend fun addNote(note: IECNote): Boolean
}

class NoteRepositoryImpl @Inject constructor(
    val noteRetrofit: NoteRemote,
    val noteDAO : NoteDao
) : NoteRepository {
    override suspend fun getNoteOffline(userID: String): List<Note> {
        if(noteDAO.getNotes().isEmpty()){
            delay(2000)
//            val noteRemote = noteRetrofit.getNotes()
//            if(noteRemote.isSuccess){
//                noteRemote.getOrNull()?.forEach {
//                    noteDAO.addNote(it.toNote())
//                }
//            }
            listOf(
                IECNote(
                    id = "1",
                    title = "title",
                    description = "content",
                    timeCreated = "1/1/2022",
                    timeUpdated = "1/1/2022"
                ),
                IECNote(
                    id = "2",
                    title = "title",
                    description = "content",
                    timeCreated = "1/1/2022",
                    timeUpdated = "1/1/2022"
                ),
                IECNote(
                    id = "3",
                    title = "title",
                    description = "content",
                    timeCreated = "1/1/2022"
                )).forEach { noteDAO.addNote(it.toNote()) }
        }
        return noteDAO.getNotes()
    }

    override suspend fun syncNote(userID: String) {
//        TODO("Not yet implemented")
    }

    override suspend fun updateNote(
        noteID: String,
        note: IECNote
    ): Boolean {
//        TODO("Not yet implemented")
        return true
    }

    override suspend fun deleteNote(noteID: String): Boolean {
//        TODO("Not yet implemented")
        return true
    }

    override suspend fun addNote(note: IECNote): Boolean {
        noteDAO.addNote(note.toNote())
        return true
    }
}