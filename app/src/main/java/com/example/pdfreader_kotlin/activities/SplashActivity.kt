package com.example.pdfreader_kotlin.activities

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.pdfreader_kotlin.databinding.ActivitySplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private var handler: Handler? = null


    private var storagePermissionLauncher: ActivityResultLauncher<String>? = null
    private val permission = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initView()
        initListener()

    }

    private fun initData() {

        handler = Handler()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        checkPermission()
    }

    private fun initView() {

    }


    private fun initListener() {

    }

    private fun splashMedia() {
        startLoading()
        handler!!.postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1300)
    }


    private fun userResponses() {

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            splashMedia()
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder(this).setTitle("Requesting permission")
                    .setMessage("Cho phép chúng tôi tìm file trên thiết bị của bạn ")
                    .setPositiveButton("Cho phép") { _, _ ->
                        storagePermissionLauncher!!.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }.setNegativeButton("Hủy bỏ") { dialogInterface, _ ->
                        Toast.makeText(this, "bạn đã từ chối tìm file", Toast.LENGTH_SHORT)
                            .show()
                        dialogInterface.dismiss()
                    }.show()
            }
        } else {
            Toast.makeText(this, "bạn đã từ chối tìm file", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermission() {
        storagePermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted: Boolean ->
            if (granted) {
                splashMedia()
            } else {
                userResponses()
            }
        }
        storagePermissionLauncher!!.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private fun startLoading() {
        // Hiển thị ProgressBar
        binding.progressBar.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.Main).launch {
            // Delay 5 giây để giả lập tác vụ
            delay(3000)
            // Ẩn ProgressBar khi hoàn tất
            binding.progressBar.visibility = View.GONE
        }
    }


}

