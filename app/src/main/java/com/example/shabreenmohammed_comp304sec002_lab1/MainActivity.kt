package com.example.shabreenmohammed_comp304sec002_lab1

import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.shabreenmohammed_comp304sec002_lab1.ui.theme.LightBlue

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shabreenmohammed_comp304sec002_lab1.ui.theme.ShabreenMohammed_Comp304Sec002_Lab1Theme
import java.util.*

data class Note(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String
)

class MainActivity : ComponentActivity() {

    // List of notes (in-memory for now)

//    private val notesList = mutableStateListOf(
//        Note(
//            id = "Note 1",
//            title = "Assignment1",
//            content = "It needs to be completed by Aug 1"
//        ),
//        Note(
//            id = "Note 2",
//            title = "Assignment2",
//            content = "It needs to be completed by Aug25"
//        )
//    )
    private val notesList = mutableStateListOf<Note>() // Empty list of notes



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShabreenMohammed_Comp304Sec002_Lab1Theme {
                val navController = rememberNavController()

                // Navigation Host to handle navigation between screens
                NavHost(navController = navController, startDestination = "home") {
                    // Home screen
                    composable("home") {
                        HomeScreen(notesList, navController)
                    }
                    // Create Note screen
                    composable("createNote") {
                        CreateNoteScreen { newNote ->
                            notesList.add(newNote)
                            navController.popBackStack() // Navigate back to home after saving
                        }
                    }
                    // Edit Note screen
                    composable("editNote/{noteId}") { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getString("noteId")
                        val note = notesList.find { it.id == noteId }
                        note?.let {
                            EditNoteScreen(it) { updatedNote ->
                                val index = notesList.indexOfFirst { it.id == noteId }
                                if (index >= 0) {
                                    notesList[index] = updatedNote // Update the note
                                }
                                navController.popBackStack() // Navigate back to home after saving
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun HomeScreen(notes: List<Note>, navController: NavHostController) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("createNote") }, containerColor = MaterialTheme.colorScheme.secondary) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Text(
                text = "Notes App",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))


            if (notes.isEmpty()) {
                Text(
                    text = "No notes available. Start by creating a new note!",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                // List of notes
                NoteList(notes, Modifier.fillMaxSize()) { note ->
                    navController.navigate("editNote/${note.id}") // Navigate to EditNoteScreen with noteId
                }
            }
        }
    }
}



// List of Notes Composable
@Composable
fun NoteList(notes: List<Note>, modifier: Modifier = Modifier, onNoteClick: (Note) -> Unit) {
    LazyColumn(modifier = modifier.fillMaxSize().padding(16.dp)) {
        items(notes.size) { index ->
            NoteCard(note = notes[index], onClick = { onNoteClick(notes[index]) })
        }
    }
}
@Composable
fun NoteCard(note: Note, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp) // Increase vertical space between cards
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.05f) // Lighter color for consistency
            containerColor = LightBlue
        ),
        shape = RoundedCornerShape(16.dp), // Rounded corners
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp) // Increased elevation for depth
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onSurface) // Text color
            )
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                maxLines = 1
            )
        }
    }
}



// Create Note Screen Composable
@Composable
fun CreateNoteScreen(onSaveClick: (Note) -> Unit) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(top = 8.dp)
        )
        Button(
            onClick = {
                val newNote = Note(title = title, content = content)
                onSaveClick(newNote)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Save")
        }
    }
}

// Edit Note Screen Composable
@Composable
fun EditNoteScreen(note: Note, onSaveClick: (Note) -> Unit) {
    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(top = 8.dp)
        )
        Button(
            onClick = {
                val updatedNote = note.copy(title = title, content = content)
                onSaveClick(updatedNote)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Save")
        }
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ShabreenMohammed_Comp304Sec002_Lab1Theme {
        HomeScreen(
            listOf(
                Note(
                    id = "Sample Note 1",
                    title = "Title of note 1",
                    content = "Content of note 1"
                ),
                Note(
                    id = "Sample Note 2",
                    title = "Title of note 2",
                    content = "Content of note 2"
                )
            ),
            rememberNavController()
        )
    }
}

