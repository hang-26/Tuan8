package com.example.towexcercises

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.oneexcerciseactivity.databinding.FileInterfaceBinding

class FileAdapter(val files: List<FileData>):
    RecyclerView.Adapter<FileAdapter.FileViewHolder>() {
    lateinit var binding: FileInterfaceBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(parent.context)
        binding = FileInterfaceBinding.inflate(view, parent, false)
        return FileViewHolder( binding )
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val listFile = files[holder.adapterPosition]
        val view = holder.binding
    }

    override fun getItemCount(): Int {
        return files.size
    }


    class FileViewHolder(val binding: FileInterfaceBinding):
        RecyclerView.ViewHolder(binding.root) {
    }
}