// file: Lkp.kt

package com.example.kursushub

data class Lkp(
    val id: String = "", // <-- Pastikan ini ada
    val nama: String? = "",
    val npsn: String? = "",
    val kecamatan: String? = "",
    val alamat: String? = "",
    val email: String? = "",
    val nomor_telepon: String? = "",
    val status_sekolah: String? = "",
    val bentuk_pendidikan: String? = "",
    val jenjang_pendidikan: String? = "",
    val program: String? = ""
)