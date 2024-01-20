package com.example.oneexcerciseactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.oneexcerciseactivity.MainActivity
import com.example.oneexcerciseactivity.R
import com.example.oneexcerciseactivity.SharePreferencesClass
import com.example.oneexcerciseactivity.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
//    var userNmae
    val mySharedPreferences:SharePreferencesClass = SharePreferencesClass(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEventSignUp()
    }


    fun setEventSignUp() {
        binding.btSignup.setOnClickListener {
            val userName = binding.etUserName.text.toString()
            val pass = binding.etPassword.text.toString()
            val rePass = binding.etPassword.text.toString()
            if (userName.isEmpty() || pass.isEmpty() || rePass.isEmpty() )
            {
                Toast.makeText(this, "Nhập mật khẩu pasword", Toast.LENGTH_SHORT).show()
            } else {
                if (rePass == pass) {
                    mySharedPreferences.putUserValues(userName, pass)
                    Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Nhắc lại mật khẩu phải khớp ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}