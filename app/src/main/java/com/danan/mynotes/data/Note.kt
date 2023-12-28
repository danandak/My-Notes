package com.danan.mynotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// merepresentasikan tabel t_note di database (model untuk t_note)
@Entity(tableName = "t_note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id : Int,

    val title : String,
    val content : String,
)