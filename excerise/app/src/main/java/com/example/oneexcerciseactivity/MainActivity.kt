package com.example.oneexcerciseactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.oneexcerciseactivity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        if (it.resultCode == Activity.RESULT_OK){
//            val e: String? = it.data?.getStringExtra("result")
        }
    }

    var mySharedPreferences: SharePreferencesClass = SharePreferencesClass(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEvent()
    }

    fun setEvent() {
        binding.tvNotAccount.setOnClickListener(View.OnClickListener {
            var i: Intent = Intent(this, RegisterActivity::class.java)
//            startActivity(i)
            startForResult.launch(intent)

        })

        binding.btLogin.setOnClickListener {
            val inputUserName = binding.etUserName.text.toString()
            val inputPass = binding.etPassword.text.toString()
            val pass = mySharedPreferences.getUserValues(inputUserName)
            Log.d("pass", "setEvent: $pass")
            if (inputPass == pass) {
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
            }
        }
    }
}