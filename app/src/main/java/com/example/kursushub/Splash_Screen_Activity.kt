package com.example.kursushub

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kursushub.databinding.ActivitySplashScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope // Pastikan ini diimpor

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var auth: FirebaseAuth // Deklarasi FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth // Inisialisasi FirebaseAuth

        lifecycleScope.launch {
            delay(2000) // Tunda selama 2 detik (sesuai kode Anda)

            // Cek status login pengguna
            val currentUser = auth.currentUser
            if (currentUser != null) {
                // Pengguna sudah login, arahkan ke MainActivity
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            } else {
                // Pengguna belum login, arahkan ke LoginActivity
                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            }
            finish() // Tutup SplashScreenActivity
        }
    }
}