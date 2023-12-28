package com.danan.mynotes.data

import androidx.lifecycle.LiveData

class NoteRepository(private val noteDao: NoteDao) {

    val readAllData: LiveData<List<Note>> = noteDao.readAllData()

    suspend fun addNote (note: Note) {
        noteDao.addNote (note)
    }

    suspend fun updateNote (note: Note) {
        noteDao.update (note)
    }

    suspend fun deleteNote (note: Note) {
        noteDao.delete (note)
    }

    fun getNoteById(id: Int): LiveData<Note> {
        return noteDao.getNoteById(id)
    }
}