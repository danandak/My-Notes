package com.danan.mynotes

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.danan.mynotes.components.AddButton
import com.danan.mynotes.components.MainTopAppBar
import com.danan.mynotes.data.MatkulDataSource
import com.danan.mynotes.data.Note
import com.danan.mynotes.data.NoteViewModel
import com.danan.mynotes.ui.theme.UtsTheme

class MainActivity : ComponentActivity() {

//    membuat variable yang merupakan instance dari NoteViewModel dengan konteks application
    private val noteViewModel: NoteViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UtsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MataKuliahApp(noteViewModel)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MataKuliahApp(noteViewModel: NoteViewModel) {
    val context = LocalContext.current

//    get all Notes from NoteViewModel
    val listNote : List<Note> by noteViewModel.getAllNotes().observeAsState(listOf())

    Scaffold(
        topBar = {
            MainTopAppBar()
        },
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            AddButton {
                Intent(context, DetailActivity::class.java).also {
                    context.startActivity(it)
                }
            }
        },
    ) {
        LoadNotes(noteList = listNote, contentPadding = it, noteViewModel = noteViewModel)
    }
}