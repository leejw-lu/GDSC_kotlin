package com.example.quiz5_4_4

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.quiz5_4_4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val customView = CustomView(this)
        binding.frameLayout.addView(customView)
    }
}

class CustomView(context: Context) : View(context) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // 라운드 사각형 (테두리 빨간, 배경색 파란.)
        val roundRec = Paint()
        roundRec.style = Paint.Style.FILL
        roundRec.color = Color.BLUE
        val rectF = RectF(100f, 450f, 600f, 650f)
        canvas?.drawRoundRect(rectF, 50f, 50f, roundRec)

        roundRec.style = Paint.Style.STROKE
        roundRec.strokeWidth = 20f
        roundRec.color = Color.RED
        canvas?.drawRoundRect(rectF, 50f, 50f, roundRec)

    }
}