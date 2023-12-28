package com.danan.mynotes

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.compose.material3.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.danan.mynotes.components.AddButton
import com.danan.mynotes.components.DetailTopAppBar
import com.danan.mynotes.components.MainTopAppBar
import com.danan.mynotes.components.SubmitButton
import com.danan.mynotes.data.MatkulDataSource
import com.danan.mynotes.data.Note
import com.danan.mynotes.data.NoteViewModel
import com.danan.mynotes.ui.theme.UtsTheme

class DetailActivity : ComponentActivity() {
    private val noteViewModel: NoteViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory(application)
    }
//    val note = noteViewModel.getNoteById(noteId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val noteId = intent.getIntExtra("NOTE_ID", 0)
//        Log.d("note_id", noteId.toString())

        setContent {
            UtsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    DetailNotes(noteViewModel = noteViewModel, noteId = noteId )
//                    DetailNotes(noteViewModel = noteViewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailNotes(noteViewModel: NoteViewModel, noteId : Int = 0) {
    val context = LocalContext.current

    val noteState = noteViewModel.getNoteById(noteId).observeAsState(null)
    val note = noteState.value

    var titleTopAppBar : String = "Add Note"

    LaunchedEffect(note) {
        if (note != null) {
            titleTopAppBar = "Edit Note"
        }
    }

    Scaffold(
        topBar = {
            DetailTopAppBar(titleTopAppBar)
        },

    ) {

//        InputNote(id = noteId,judul = note.title, konten = konten, contentPadding = it)
        InputNote(note = note, contentPadding = it)
//        InputNote(contentPadding = it)
//        note?.let {
//            InputNote(judul = it.title ?: "", konten = it.content ?: "", contentPadding = it)
//        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun InputNote(
//    id : Int = 0,
//    judul : String = "",
//    konten : String = "",
    note: Note?,
    contentPadding : PaddingValues = PaddingValues(0.dp),
) {
    val context = LocalContext.current

    var title by remember {
        mutableStateOf(note?.title.orEmpty())
    }
    var content by remember {
        mutableStateOf(note?.content.orEmpty())
    }

    var id_note by remember {
        mutableStateOf(note?.id ?: 0)
    }

    LaunchedEffect(note) {
        if (note != null) {
            title = note.title
            content = note.content
            id_note = note.id
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = {newText ->
                title = newText
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.primary),
            label = { Text(text = "Judul") },
            modifier = Modifier
                .padding(top = 16.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = content,
            onValueChange = {newText ->
                content = newText
            },
            textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.primary),
            label = { Text(text = "Konten") },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(300.dp),
            maxLines = 10
        )
        SubmitButton (
            onClick = {
                val note = Note(id_note, title = title, content = content)
//                val model : NoteViewModel = intent.getSerializableExtra("NOTE_VIEW_MODEL") as NoteViewModel
                var model = ViewModelProvider(context as DetailActivity).get(NoteViewModel::class.java)

                if(id_note > 0) {
                    model.updateNote(
                        note = note
                    )
                    Toast.makeText(context, "Note berhasil diupdate", Toast.LENGTH_LONG).show()
                }
                else {
                    model.addNote(
                        note = note
                    )
                    Toast.makeText(context, "Note berhasil ditambahkan", Toast.LENGTH_LONG).show()
                }
//                model.addNote(
//                    note = note
//                )


                Intent(context, MainActivity::class.java).also {
                    context.startActivity(it)
                }
            },
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
                .align(Alignment.End)
        )
    }
}

fun updateNote(note: Note, model: NoteViewModel) {
    model.updateNote(note)

}