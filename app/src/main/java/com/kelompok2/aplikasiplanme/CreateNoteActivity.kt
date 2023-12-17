import android.app.DatePickerDialog
import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.IgnoreExtraProperties
import com.kelompok2.aplikasiplanme.R
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteActivity : AppCompatActivity() {

    private lateinit var judulEditText: EditText
    private lateinit var isiEditText: EditText
    private lateinit var tanggalButton: Button
    private lateinit var databaseReference: DatabaseReference

    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        // Inisialisasi Firebase
        databaseReference = FirebaseDatabase.getInstance().reference

        judulEditText = findViewById(R.id.etTitle)
        isiEditText = findViewById(R.id.etIsicatatan)
        tanggalButton = findViewById(R.id.btnSimpan)

        // Button untuk menampilkan DatePickerDialog
        tanggalButton.setOnClickListener {
            tampilkanDatePicker()
        }

        // Button untuk menyimpan catatan
        val simpanButton = findViewById<Button>(R.id.btnSimpan)
        simpanButton.setOnClickListener {
            simpanCatatan()
        }
    }

    private fun tampilkanDatePicker() {
        val calendar = Calendar.getInstance()
        val tahun = calendar.get(Calendar.YEAR)
        val bulan = calendar.get(Calendar.MONTH)
        val hari = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                // Update properti dateline
                selectedDate = "$year-${monthOfYear + 1}-${dayOfMonth}"

                // Format tanggal yang diinginkan
                val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                // Tampilkan dateline pada tombol (contoh, bisa diubah sesuai kebutuhan)
                tanggalButton.text = formatter.format(calendar.time)
            },
            tahun,
            bulan,
            hari
        )

        datePickerDialog.show()
    }

    private fun simpanCatatan() {
        val judul = judulEditText.text.toString().trim()
        val isi = isiEditText.text.toString().trim()

        if (judul.isNotEmpty() && isi.isNotEmpty() && selectedDate.isNotEmpty()) {
            // Buat objek Catatan
            val catatan = Catatan(judul = judul, isi = isi, dateline = selectedDate)

            // Simpan data ke Firebase
            val catatanKey = databaseReference.child("catatan").push().key
            catatanKey?.let {
                databaseReference.child("catatan").child(it).setValue(catatan)
            }
            // Setelah menyimpan catatan ke Firebase, tambahkan logika untuk menampilkan di halaman list
            val intent = Intent(this@CreateNoteActivity, ListActivity::class.java)
            intent.putCatatanExtra(catatan)
            startActivity(intent)
            finish()


            // Tambahkan logika lainnya seperti kembali ke halaman sebelumnya atau menampilkan pesan sukses
            finish()
        } else {
            // Tambahkan logika jika data tidak lengkap
            // Misalnya, munculkan pesan kesalahan
        }
    }

    fun Intent.putCatatanExtra(catatan: Catatan) {
        putExtra("catatan", catatan)
    }

    private fun putExtra(s: String, catatan: Catatan) {

    }




}
class Catatan(val judul: String = "", val isi: String = "", val dateline: String = "")