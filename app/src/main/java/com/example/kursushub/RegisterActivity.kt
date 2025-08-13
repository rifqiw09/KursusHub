package com.example.kursushub

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kursushub.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inisialisasi View Binding
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi Firebase Auth dan Firestore
        auth = Firebase.auth
        db = Firebase.firestore

        // Atur onClickListener untuk tombol Daftar
        binding.btnDaftar.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val namaLengkap = binding.etNamaLengkap.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val nomorTelepon = binding.etNomorTelepon.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val konfirmasiPassword = binding.etKonfirmasiPassword.text.toString().trim()

            // Validasi Input
            if (username.isEmpty() || namaLengkap.isEmpty() || email.isEmpty() ||
                nomorTelepon.isEmpty() || password.isEmpty() || konfirmasiPassword.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != konfirmasiPassword) {
                Toast.makeText(this, "Password tidak cocok!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!binding.cbSyaratKetentuan.isChecked) {
                Toast.makeText(this, "Anda harus menyetujui syarat dan ketentuan!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Panggil fungsi registrasi
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Pendaftaran berhasil, simpan data tambahan ke Firestore
                        val user = auth.currentUser
                        val userId = user?.uid

                        if (userId != null) {
                            val userProfile = hashMapOf(
                                "username" to username,
                                "namaLengkap" to namaLengkap,
                                "email" to email,
                                "nomorTelepon" to nomorTelepon
                            )

                            db.collection("users").document(userId)
                                .set(userProfile)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "Gagal menyimpan data: ${e.message}", Toast.LENGTH_SHORT).show()
                                    user?.delete()
                                }
                        }
                    } else {
                        Toast.makeText(this, "Registrasi Gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}