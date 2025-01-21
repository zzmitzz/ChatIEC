package com.example.iec.data.repository

import com.example.data.IECNote
import com.example.iec.data.remote.NoteRemote
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    val noteRetrofit: NoteRemote
) : NoteRepository {
    override suspend fun getNoteOffline(userID: String): List<IECNote> {
        // TODO("Not yet implemented")
        delay(2000)
        return listOf(
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
            )
        )
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
//        TODO("Not yet implemented")
        return true
    }
    fun getNotes(userID: String) = callbackFlow<
            List<IECNote>> {
        trySend(getNoteOffline(userID))
        awaitClose {
        }
    }
}