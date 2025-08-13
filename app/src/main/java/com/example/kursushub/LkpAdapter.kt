package com.example.kursushub

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kursushub.databinding.ItemLkpCardBinding

class LkpAdapter(private val lkpList: List<Lkp>) : RecyclerView.Adapter<LkpAdapter.LkpViewHolder>() {

    class LkpViewHolder(val binding: ItemLkpCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LkpViewHolder {
        val binding = ItemLkpCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LkpViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LkpViewHolder, position: Int) {
        val lkp = lkpList[position]
        holder.binding.tvLkpName.text = lkp.nama

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailLKPActivity::class.java)
            // Mengirim ID dokumen LKP yang dipilih ke halaman detail
            intent.putExtra("lkp_id", lkp.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return lkpList.size
    }
}