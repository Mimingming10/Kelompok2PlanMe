package com.kelompok2.aplikasiplanme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kelompok2.aplikasiplanme.room.Note
import com.kelompok2.aplikasiplanme.room.NoteDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateNoteActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)
        setupListener()
    }
    fun setupListener(){
        CoroutineScope(Dispatchers.IO).launch{
            NoteDB(this@CreateNoteActivity).noteDao().addNote(
                Note(
                    "a","a",1
                )
            )

        }
    }
}