package com.example.chapter6

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.chapter6.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnCamera.setOnClickListener {
            requirePermissions(arrayOf(Manifest.permission.CAMERA),10)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==10) {
            if(resultCode== Activity.RESULT_OK){
                Log.d("카메라","촬영성공")
            }
            else{
                Log.d("카메라","촬영실패")
            }
        }
    }

    override fun permissionGranted(requestCode: Int){
        val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent,99)
    }

    override fun permissionDenied(requestCode: Int) {
        Toast.makeText(baseContext,"권한 거부됨",Toast.LENGTH_LONG).show()
    }
}