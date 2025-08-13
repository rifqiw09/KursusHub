package com.example.kursushub

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.kursushub.databinding.ActivityDetailLkpBinding

class DetailLKPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailLkpBinding
    private lateinit var db: com.google.firebase.firestore.FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailLkpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Firebase.firestore

        // Ambil ID dokumen dari Intent
        val lkpId = intent.getStringExtra("lkp_id")

        if (lkpId != null) {
            fetchLkpDetails(lkpId)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun fetchLkpDetails(lkpId: String) {
        db.collection("lkps").document(lkpId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val lkp = document.toObject(Lkp::class.java)
                    if (lkp != null) {
                        binding.tvLkpName.text = lkp.nama

                        val details = """
                            NPSN: ${lkp.npsn ?: "Tidak tersedia"}
                            Alamat: ${lkp.alamat ?: "Tidak tersedia"}
                            Email: ${lkp.email ?: "Tidak tersedia"}
                            Status Sekolah: ${lkp.status_sekolah ?: "Tidak tersedia"}
                            Bentuk Pendidikan: ${lkp.bentuk_pendidikan ?: "Tidak tersedia"}
                            Jenjang Pendidikan: ${lkp.jenjang_pendidikan ?: "Tidak tersedia"}
                            Program Layanan: ${lkp.program ?: "Tidak tersedia"}
                        """.trimIndent()
                        binding.tvLkpDetails.text = details

                        binding.btnHubungiLKP.setOnClickListener {
                            val phoneNumber = lkp.nomor_telepon
                            if (!phoneNumber.isNullOrEmpty()) {
                                val intent = Intent(Intent.ACTION_DIAL)
                                intent.data = Uri.parse("tel:$phoneNumber")
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "Nomor telepon tidak tersedia.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Gagal mengambil detail LKP: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}