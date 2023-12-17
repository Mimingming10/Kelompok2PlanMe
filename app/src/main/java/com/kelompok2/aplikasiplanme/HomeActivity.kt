package com.kelompok2.aplikasiplanme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kelompok2.aplikasiplanme.room.Note
import com.kelompok2.aplikasiplanme.room.NoteDB
import com.kelompok2.aplikasiplanme.room.NoteDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {
    lateinit var noteAdapter: NoteAdapter
    private lateinit var db: NoteDao
    private var list_note: RecyclerView = findViewById(/* id = */ R.id.listitem)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart(

        )
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.getNote()
            Log.d("HomeActivity","dbResponse: $notes")
            withContext(Dispatchers.Main) {
                noteAdapter.setData(notes)
            }
        }
    }
    fun setupListener() {
        val tambah = findViewById<FloatingActionButton>(R.id.tambah)
        tambah.setOnClickListener{
            startActivity(Intent(this,CreateNoteActivity::class.java))
        }
    }
    private fun setupRecyclerView(){
        noteAdapter = NoteAdapter(arrayListOf())
        list_note.apply{
            var layoutManagger = LinearLayoutManager(applicationContext)
            var adapter = noteAdapter
        }
    }}

