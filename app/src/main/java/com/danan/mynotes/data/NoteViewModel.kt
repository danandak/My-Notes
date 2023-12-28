package com.danan.mynotes.data

import android.app.Application
import android.os.Parcelable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Serializable

class NoteViewModel(application: Application) : AndroidViewModel(application), Serializable {

    private val readAllData: LiveData<List<Note>>
    private val repository: NoteRepository
    init {
        val noteDao = MyNotesDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
        readAllData = repository.readAllData
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return readAllData
    }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(note)
        }
    }

    fun updateNote (note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)
        }
    }

    fun deleteNote (note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }

    fun getNoteById(id: Int): LiveData<Note> {
        return repository.getNoteById(id)
    }
}