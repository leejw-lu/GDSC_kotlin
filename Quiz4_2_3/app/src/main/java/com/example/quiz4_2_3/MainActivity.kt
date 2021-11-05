package com.example.quiz4_2_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quiz4_2_3.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        thread(start=true) {                //서브 스레드
            for (i in 0 until 10) {
                Thread.sleep(1000)
                runOnUiThread {             //메인 스레드
                    binding.textView.text = "${i+1}"
                }
            }
        }
    }
}