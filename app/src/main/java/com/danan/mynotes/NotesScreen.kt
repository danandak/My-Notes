package com.danan.mynotes

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.danan.mynotes.components.AlertDialogExample
import com.danan.mynotes.components.deleteButton
import com.danan.mynotes.components.editButton
import com.danan.mynotes.data.Note
import com.danan.mynotes.data.NoteViewModel
//import com.danan.mynotes.model.Note
//import androidx.compose.foundation.lazy.items

@Composable
fun LoadNotes(noteList: List<Note>, contentPadding : PaddingValues = PaddingValues(0.dp), noteViewModel: NoteViewModel) {
    if(noteList.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Tidak ada note",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "Silahkan tambahkan note baru",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                )
            }
        }
    }
    else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(300.dp),
            contentPadding = contentPadding,
            modifier = Modifier
                .padding(bottom = 50.dp, top = 12.dp)
        ) {
            items(noteList) { note ->
                NoteCard(
                    note = note,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                        .height(130.dp)
                )
            }
        }
    }
}

@Composable
//@ExperimentalMaterial3Api
fun NoteCard(note : Note, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var openAlertDialog = remember { mutableStateOf(false) }

    Card (
        modifier = modifier,
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp),
            ) {
                Text(
                    text = note.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = note.content,
                )
            }

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
            ) {
                editButton(
                    onClick = {
                        val intent = Intent(context, DetailActivity::class.java).apply {
                            putExtra("NOTE_ID", note.id)
                        }

                        context.startActivity(intent)
                    },
                    modifier = Modifier.align(Alignment.End)
                )
                deleteButton(
                    onClick = { openAlertDialog.value = true },
                    modifier = Modifier.align(Alignment.End)
                )

                if (openAlertDialog.value) {
                    AlertDialogExample(
                        onDismissRequest = { openAlertDialog.value = false },
                        onConfirmation = {
                            val model : NoteViewModel = ViewModelProvider(context as MainActivity).get(NoteViewModel::class.java)

                            model.deleteNote(
                                note = note
                            )

                            openAlertDialog.value = false

                            Toast.makeText(context, "Note berhasil dihapus", Toast.LENGTH_SHORT).show()
                        },
                        dialogTitle = "Note akan dihapus",
                        dialogText = "Note dengan judul berikut akan dihapus : \n" +
                                "\n" +
                                "\"${note.title}\"\n" +
                                "\nApakah anda yakin ingin menghapus note ini? Aksi ini tidak bisa dibatalkan",
                        icon = Icons.Default.Warning,
                        confirmText = "Hapus",
                        dismissText = "Batal"
                    )
                }
            }
        }

    }
}

//@Composable
//fun OpenDeleteAlert( open : Boolean ) {
//    val openAlertDialog = remember { mutableStateOf(false) }
//    // ...
//    when {
//        // ...
//        openAlertDialog.value -> {
//            AlertDialogExample(
//                onDismissRequest = { openAlertDialog.value = false },
//                onConfirmation = {
//                    val model : NoteViewModel = ViewModelProvider(context as MainActivity).get(NoteViewModel::class.java)
//
//                    model.deleteNote(
//                        note = note
//                    )
//
//                    openAlertDialog.value = false
//
//
//                    println("Confirmation registered") // Add logic here to handle confirmation.
//                },
//                dialogTitle = "Alert dialog example",
//                dialogText = "This is an example of an alert dialog with buttons.",
//                icon = Icons.Default.Info
//            )
//        }
//    }
//}