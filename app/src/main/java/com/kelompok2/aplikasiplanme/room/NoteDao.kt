package com.kelompok2.aplikasiplanme.room

import androidx.room.*

@Dao
interface NoteDao {
    @Insert
    fun addNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("SELECT * FROM note")
    fun getNote(): List<Note>
}