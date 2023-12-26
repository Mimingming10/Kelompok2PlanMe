package com.kelompok2.aplikasiplanme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.*
import com.kelompok2.aplikasiplanme.databinding.ActivityHomeBinding
import java.util.*
import kotlin.collections.ArrayList
import androidx.recyclerview.widget.RecyclerView






class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var dataList: ArrayList<DataClassNote>
    private lateinit var adapter: Adapter
    var databaseReference:DatabaseReference? = null
    var eventListener:ValueEventListener? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)




        // Menghapus title project pada bagian atas
        supportActionBar?.hide()

     //  val gridLayoutManager = GridLayoutManager(this@HomeActivity, 1)
     //  binding.recyclerView.layoutManager = gridLayoutManager

        val builder = AlertDialog.Builder(this@HomeActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_dialog)
        val dialog = builder.create()
        dialog.show()
        dataList = ArrayList()
        adapter = Adapter(this@HomeActivity, dataList)
      //  binding.recyclerview.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("Plan ME")
        dialog.show()

        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(DataClassNote::class.java)
                    if (dataClass != null) {
                        dataList.add(dataClass)
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }
        })

        binding.tambah.setOnClickListener {
            val intent = Intent(this, CreateNoteActivity::class.java)
            startActivity(intent)
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return true
            }
        })
    }

    fun searchList(text: String) {
        val searchList = java.util.ArrayList<DataClassNote>()
        for (dataClass in dataList) {
            if (dataClass.dataTitle?.lowercase()
                    ?.contains(text.lowercase(Locale.getDefault())) == true
            ) {
                searchList.add(dataClass)
            }
        }
        adapter.searchDataList(searchList)
    }
}



