package com.example.oneexcerciseactivity.towexcercises

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.oneexcerciseactivity.databinding.FileInterfaceBinding

class FileAdapter(var files: List<FileData>):
    RecyclerView.Adapter<FileAdapter.FileViewHolder>() {
    lateinit var binding: FileInterfaceBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(parent.context)
        binding = FileInterfaceBinding.inflate(view, parent, false)
        return FileViewHolder( binding )
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val listFile = files[position]
        val view = holder.binding
        view.tvName.text = listFile.name
        view.tvPath.text = listFile.path
        view.tvSize.text = formatFileSize(listFile.size)
        view.ivIcon.setImageResource(listFile.icon)
    }

    override fun getItemCount(): Int {
        return files.size
    }


    class FileViewHolder(val binding: FileInterfaceBinding):
        RecyclerView.ViewHolder(binding.root) {
    }

    fun formatFileSize(size: Long): String {
        // Implement logic to format file size (e.g., KB, MB, GB)
        val fileSizeInkB = size/1024
        when  {
            fileSizeInkB < 1024 -> return "$fileSizeInkB KB"
            fileSizeInkB < 1024 * 1024 -> return "${fileSizeInkB / 1024} MB"
            else -> return "${fileSizeInkB / (1024 * 1024)} GB"
        }
        return "$size Bytes"
    }

}