package com.danan.mynotes.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

// logic yang digunakan untuk mengakses database melalui entity tertentu (dalam hal ini entity Note)
@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM t_note ORDER BY id ASC")
    fun readAllData() : LiveData<List<Note>>

    @Query("SELECT * FROM t_note WHERE id = :id")
    fun getNoteById(id: Int): LiveData<Note>
}