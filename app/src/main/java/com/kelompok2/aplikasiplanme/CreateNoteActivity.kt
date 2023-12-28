package com.kelompok2.aplikasiplanme

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.kelompok2.aplikasiplanme.databinding.ActivityCreateNoteBinding
import java.net.URI
import java.text.DateFormat
import java.util.Calendar


class CreateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNoteBinding
    var imageURL: String? = null
    var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menghapus title project pada bagian atas
        supportActionBar?.hide()

        // Aktivitas memilih gambar
        val activityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == RESULT_OK ){
                val data = result.data
                uri = data!!.data
                binding.uploadImage.setImageURI(uri)
            } else {
                Toast.makeText(this@CreateNoteActivity, "No Image Selected", Toast.LENGTH_SHORT).show()
            }
        }
        // Mengambil gambar dari perangkat
        binding.uploadImage.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }
        binding.btnSave.setOnClickListener {
            saveData()
        }
    }

    private fun saveData(){
        // Membuat referensi penyimpanan tugas
        val storageReference = FirebaseStorage.getInstance().reference.child("Task Images")
            .child(uri!!.lastPathSegment!!)
        // Membuat alertdialog untuk menampilkan kemajuan dari hasil simpan
        val builder = AlertDialog.Builder(this@CreateNoteActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_dialog)
        val dialog = builder.create()
        dialog.show()
        // Meyimpan gambar pada firebase storage
        storageReference.putFile(uri!!).addOnSuccessListener { taskSnapshot ->
            // Get url unduh dari unggahan gambar
            val uriTask = taskSnapshot.storage.downloadUrl
            while (!uriTask.isComplete);
            // Get url gambar yang diunggah
            val urlImage = uriTask.result
            imageURL = urlImage.toString()
            uploadData()
            dialog.dismiss()
        }.addOnFailureListener {
            dialog.dismiss()
        }
    }

    private fun uploadData(){
        // Mengambil data dari input
        val title = binding.uploadTitle.text.toString()
        val desc = binding.uploadDesc.text.toString()
        val priority = binding.uploadPriority.text.toString()
        val dataClass = DataClassNote(title, desc, priority, imageURL)
        // Tanggal dan waktu sebagai string
        val currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)
        // Proses akses firebase realtime
        FirebaseDatabase.getInstance().getReference("Plan Me").child(currentDate)
            .setValue(dataClass).addOnCompleteListener { task ->
                // Periksa proses sukses
                if (task.isSuccessful) {
                    // Menampilkan pesan sukses
                    Toast.makeText(this@CreateNoteActivity, "Tersimpan", Toast.LENGTH_SHORT).show()
                    finish()
                }
                // Menampilkan pesan error
            }.addOnFailureListener { e ->
                Toast.makeText(
                    this@CreateNoteActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}