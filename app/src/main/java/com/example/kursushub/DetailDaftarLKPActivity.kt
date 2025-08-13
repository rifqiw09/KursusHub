// file: DetailDaftarLKPActivity.kt

package com.example.kursushub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.kursushub.databinding.ActivityDetailDaftarLkpBinding

class DetailDaftarLKPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailDaftarLkpBinding
    private lateinit var db: com.google.firebase.firestore.FirebaseFirestore
    private lateinit var lkpAdapter: LkpAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDaftarLkpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Firebase.firestore

        // Ambil nama kecamatan dari Intent
        val kecamatanName = intent.getStringExtra("kecamatan_name")

        // Perbaiki di sini
        if (kecamatanName != null) {
            // Hapus awalan "Kecamatan " agar nama sesuai dengan data di Firestore
            val firestoreKecamatanName = kecamatanName.replace("Kecamatan ", "")

            // Set judul halaman
            binding.tvDistrictTitle.text = kecamatanName
            binding.tvDaftarTitle.text = "Daftar LKP $firestoreKecamatanName"

            fetchLkpListByDistrict(firestoreKecamatanName)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun fetchLkpListByDistrict(kecamatan: String) {
        val lkpList = mutableListOf<Lkp>()
        db.collection("lkps")
            .whereEqualTo("kecamatan", kecamatan)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val lkp = document.toObject(Lkp::class.java).copy(id = document.id) // <-- PERBAIKAN DI SINI
                    lkpList.add(lkp)
                }

                lkpAdapter = LkpAdapter(lkpList)
                binding.rvLkpList.layoutManager = LinearLayoutManager(this)
                binding.rvLkpList.adapter = lkpAdapter
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Gagal mengambil data LKP: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}