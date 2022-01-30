package com.example.chapter9

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.chapter9.databinding.ActivityMainBinding
import java.io.IOException
import java.text.SimpleDateFormat

class MainActivity : BaseActivity() {

    val PERM_STORAGE= 99 // 외부 저장소 권한 처리
    val PERM_CAMERA= 100 // 카메라 권한 처리
    val REQ_CAMERA= 101 // 카메라 촬영 요청
    val REQ_STORAGE = 102

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    var realUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        requirePermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),PERM_STORAGE)
    }

    override fun permissionGranted(requestCode: Int) {
        when (requestCode) {
            PERM_STORAGE -> setViews()
            PERM_CAMERA -> openCamera()  //추가
        }
    }

    override fun permissionDenied(requestCode: Int) {
        when (requestCode){
            PERM_STORAGE -> {
                Toast.makeText(baseContext,"외부 저장소 권한을 승인해야 앱을 사용할 수 있습니다.", Toast.LENGTH_LONG).show()
                finish()
            }
            PERM_CAMERA -> {
                Toast.makeText(baseContext,"카메라 권한을 승인해야 앱을 사용할 수 있습니다.", Toast.LENGTH_LONG).show()
            }

        }
    }

    fun setViews(){
        binding.buttonCamera.setOnClickListener {
            requirePermissions(arrayOf(Manifest.permission.CAMERA),PERM_CAMERA)     //카메라 권한 요청
        }
        binding.buttonGallery.setOnClickListener {
            openGallery()
        }
    }

    fun openGallery(){
        val intent= Intent(Intent.ACTION_PICK)     // intent.type에서 설정한 종류의 데이터를 MediaStore에 불러와 목록으로 나열 후 선택할 수 있는 이미지 앱이 실행됨.
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, REQ_STORAGE)
    }

    fun openCamera(){   //카메라 요청
        val intent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        createImageUri(newFileName(),"image/jpg")?.let { uri ->
            realUri=uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, realUri)
            startActivityForResult(intent,REQ_CAMERA)
        }
    }

    fun createImageUri(filename: String, mimeType: String): Uri?{
        val values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

    }

    fun newFileName(): String{
        val sdf= SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename=sdf.format(System.currentTimeMillis())

        return "$filename.jpg"
    }

    fun loadBitmap(photoUri: Uri): Bitmap? {
        var image :Bitmap? = null
        try{
            image= if (Build.VERSION.SDK_INT > 27) { //API 버전별 이미지를 처리
                val source: ImageDecoder.Source=
                    ImageDecoder.createSource(this.contentResolver, photoUri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(this.contentResolver, photoUri)
            }

        } catch (e:IOException){
            e.printStackTrace()
        }
        return image
    }

    //촬영한 사진 정보는 세번째 파라미터인 data에 인텐트로 전달된다.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK){
            when (requestCode) {
                REQ_CAMERA -> {
                    realUri?.let { uri ->
                        val bitmap = loadBitmap(uri)
                        binding.imagePreview.setImageBitmap(bitmap)

                        realUri=null
                    }
                }
                REQ_STORAGE -> {
                    data?.data?.let {uri ->
                        binding.imagePreview.setImageURI(uri)
                    }
                }
            }
        }
    }


}