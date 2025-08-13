package com.example.kursushub

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kursushub.DetailDaftarLKPActivity
import com.example.kursushub.District
import com.example.kursushub.databinding.ItemDistrictCardBinding
import kotlin.jvm.java


class DistrictAdapter(private val districts: List<District>) : RecyclerView.Adapter<DistrictAdapter.DistrictViewHolder>() {

    class DistrictViewHolder(val binding: ItemDistrictCardBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistrictViewHolder {

        val binding = ItemDistrictCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DistrictViewHolder(binding)
    }
    override fun onBindViewHolder(holder: DistrictViewHolder, position: Int) {
        val district = districts[position]
        holder.binding.tvDistrictName.text = district.name

        holder.itemView.setOnClickListener {

            val context = holder.itemView.context

            val intent = Intent(context, DetailDaftarLKPActivity::class.java)

            intent.putExtra("kecamatan_name", district.name)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return districts.size
    }

}