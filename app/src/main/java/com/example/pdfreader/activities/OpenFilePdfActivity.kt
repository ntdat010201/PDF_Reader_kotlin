package com.example.pdfreader.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pdfreader.model.ModelFileItem
import com.example.pdfreaderkotlin.R
import com.example.pdfreaderkotlin.databinding.ActivityOpenFilePdfBinding
import com.github.barteksc.pdfviewer.util.FitPolicy
import java.io.File


class OpenFilePdfActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOpenFilePdfBinding
    private var file: ModelFileItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenFilePdfBinding.inflate(LayoutInflater.from(this))
        initData()
        initView()
        initListener()

        setContentView(R.layout.activity_open_file_pdf)
    }

    private fun initData() {
        file = intent.getParcelableExtra("data_pdf")
        Log.d("DAT", "initData: ${file!!.path}")
    }

    private fun initView() {
        if (file == null) {
            Toast.makeText(this, "file không tồn tại", Toast.LENGTH_SHORT).show()
            onBackPressedDispatcher.onBackPressed()
        } else {

            val file = File(file!!.path)

            binding.pdfView.fromFile(file)
                .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
                .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view
                .fitEachPage(false) // fit each page to the view, else smaller pages are scaled relative to largest page.
                .pageSnap(false) // snap pages to screen boundaries
                .pageFling(false) // make a fling change only a single page like ViewPager
                .nightMode(false) // toggle night mode
                .onError {
                    Log.d("DAT", "initView: 1")
                }
                .onRender {
                    Log.d("DAT", "initView:2 ")
                }
                .onLoad {
                    Log.d("DAT", "initView: 3")
                }
                .load();
        }
    }

    private fun initListener() {

    }
}