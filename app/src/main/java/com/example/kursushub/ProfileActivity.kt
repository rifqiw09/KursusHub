package com.example.kursushub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.kursushub.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: com.google.firebase.firestore.FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = Firebase.firestore

        binding.ivBack.setOnClickListener {
            finish()
        }

        loadUserProfile()
    }

    private fun loadUserProfile() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val username = document.getString("username")
                        val fullName = document.getString("fullName")
                        val phoneNumber = document.getString("phoneNumber")
                        val email = currentUser.email

                        binding.tvUsername.text = username ?: "Nama Pengguna"
                        binding.etFullName.setText(fullName)
                        binding.etPhoneNumber.setText(phoneNumber)
                        binding.etEmail.setText(email)
                    }
                }
                .addOnFailureListener {
                    // Handle error
                }
        }
    }
}