package com.kelompok2.aplikasiplanme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kelompok2.aplikasiplanme.room.Note
import kotlinx.coroutines.NonDisposableHandle.parent

class NoteAdapter (private val notes: ArrayList<Note>) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : NoteViewHolder {
         return NoteViewHolder(
             LayoutInflater.from(parent.context).inflate(R.layout.adapter_note, parent, false))
        
     }

     override fun getItemCount()= notes.size

     override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
         val note = notes[position]
         holder.tv.text = note.inputcatatan
     }

     class NoteViewHolder(val view:View) : RecyclerView.ViewHolder(view) {
         val tv = view.findViewById<TextView>(R.id.tvTitle)
     }


     fun setData(note: List<Note>) {
         notes.clear()
         notes.addAll(note)
         notifyDataSetChanged()
     }
 }