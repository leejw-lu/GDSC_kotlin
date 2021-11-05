package com.example.quiz4_2_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quiz4_2_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.radioApple -> binding.textView.text = "사과"
                R.id.radioBanana -> binding.textView.text = "바나나"
                R.id.radioOrange -> binding.textView.text = "오렌지"
            }
        }
    }
}