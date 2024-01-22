package com.example.oneexcerciseactivity.towexcercises

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oneexcerciseactivity.R
import com.example.oneexcerciseactivity.databinding.ActivityRecyclerViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class RecyclerViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityRecyclerViewBinding
    val filesMode = mutableListOf<FileData>()
    lateinit var fileAdapter: FileAdapter
    private val READ_EXTERNAL_STORAGE_REQUEST = 123 // Mã yêu cầu quyền đọc external storage
    var status = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerViewFile = binding.rvFile
        fileAdapter = FileAdapter(filesMode)
        recyclerViewFile.adapter = fileAdapter
        recyclerViewFile.layoutManager = LinearLayoutManager(this)


        /**
         * Kiểm tra quyền
         */
        checkAndRequestExternal()

    }

    private fun setEnvent() {
        binding.btScan.setOnClickListener {
            status = true

            if (status == true) {
                CoroutineScope (Dispatchers.IO).launch {
                    val files = scanMemoryCard()
                    withContext(Dispatchers.Main) {
                        filesMode.addAll(files)
                        fileAdapter.notifyDataSetChanged()
                    }
                    delay (1000)
                }
            }
        }

        binding.btStop.setOnClickListener {
            Toast.makeText(this, "Tạm dừng tìm kiếm", Toast.LENGTH_SHORT).show()
            status = false
        }
    }

    fun checkAndRequestExternal() {
        //kiểm tra quyền được cấp hay chưa
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)  != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_STORAGE_REQUEST
            )
        } else {
            binding.btScan.setOnClickListener {
                Toast.makeText(this, "Đã cấp quyền", Toast.LENGTH_SHORT).show()
                setEnvent()
            }
        }
    }

     fun scanMemoryCard(): List<FileData> {
         // Sử dụng mediaStore để cập nhật danh sách ảnh
        val files = mutableListOf<FileData>()

        val uri = MediaStore.Files.getContentUri("external")
        val projection = arrayOf(
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Files.FileColumns.MIME_TYPE,
        )

        val selection = "${MediaStore.Files.FileColumns.MEDIA_TYPE} = ${MediaStore.Files.FileColumns.MEDIA_TYPE_NONE} OR " +
                "${MediaStore.Files.FileColumns.MEDIA_TYPE} = ${MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE} OR " +
                "${MediaStore.Files.FileColumns.MEDIA_TYPE} = ${MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO}"
        contentResolver.query(uri, projection, selection, null, null)?.use { cursor ->
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)
            val mimeTypeColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE)

            while (cursor.moveToNext()) {
                val name = cursor.getString(nameColumn)
                val path = cursor.getString(pathColumn)
                val size = cursor.getLong(sizeColumn)
                val mimeType = cursor.getString(mimeTypeColumn)

                val iconResId = getIconResId(mimeType)

                files.add(FileData(name, path, size, iconResId))
            }
        }

        return files
    }

    fun getIconResId(mimeType: String?): Int {
        if (mimeType.isNullOrEmpty()) {
            return R.drawable.ic_files
        }

        val lowerCaseMimeType = mimeType.toLowerCase(Locale.ROOT)

        when {
            lowerCaseMimeType.contains("image") -> return R.drawable.ic_image
            lowerCaseMimeType.contains("audio") -> return R.drawable.ic_audio
            lowerCaseMimeType.contains("video") -> return R.drawable.ic_video
            lowerCaseMimeType.contains("text") -> return R.drawable.ic_txt
            lowerCaseMimeType.contains("application/pdf") -> return R.drawable.ic_pdf
            else -> return R.drawable.ic_files
        }
    }

//    suspend fun updateProcessBar() {
//        val process
////        val process = caculateProgress()
//        withContext(Dispatchers.Main) {
//            binding.pbProcessBar.progress = process
//            if (process == 100) {
//                binding.pbProcessBar.visibility = View.GONE
//            } else {
//                binding.pbProcessBar.visibility = View.VISIBLE
//            }
//        }
//    }

//    fun caculateProgress(): Int {
//        var totalMemory = Environment.getExternalStorageDirectory().totalSpace
//        var freeMediaStore = Environment.getExternalStorageDirectory().freeSpace
//        totalMemory = totalMemory / 1024
//        freeMediaStore = freeMediaStore / 1024
//    }

}