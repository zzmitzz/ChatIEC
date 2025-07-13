package com.example.iec.data.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import com.example.data.IECNote


@Entity ( tableName = "notes")
data class Note (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String = "",
    val description: String = "",
    val timeCreated: String = "",
    val timeUpdated: String = ""
){
    fun toIECNote(): IECNote = IECNote(title, description, timeCreated, timeUpdated)
}


fun IECNote.toNote(): Note = Note(id.toLong(), title, description, timeCreated, timeUpdated)

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    suspend fun getNotes(): List<Note>

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note): Long
}