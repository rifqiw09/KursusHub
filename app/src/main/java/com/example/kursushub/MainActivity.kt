package com.example.kursushub

import com.example.kursushub.DistrictAdapter
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.kursushub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: com.google.firebase.firestore.FirebaseFirestore
    private lateinit var districtAdapter: DistrictAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Firebase.firestore
        districtAdapter = DistrictAdapter(emptyList())
        binding.rvDistricts.layoutManager = LinearLayoutManager(this)
        binding.rvDistricts.adapter = districtAdapter

        fetchDataFromFirestore()

        binding.ivProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchDataFromFirestore() {
        val lkpCountByDistrict = mutableMapOf<String, Int>()

        db.collection("lkps")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val kecamatan = document.getString("kecamatan")
                    if (kecamatan != null) {
                        lkpCountByDistrict[kecamatan] = (lkpCountByDistrict[kecamatan] ?: 0) + 1
                    }
                }

                val districtList = lkpCountByDistrict.map { (name, count) ->
                    District("Kecamatan $name")
                }.sortedBy { it.name }
                districtAdapter = DistrictAdapter(districtList)
                binding.rvDistricts.adapter = districtAdapter
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Gagal mengambil data: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

}