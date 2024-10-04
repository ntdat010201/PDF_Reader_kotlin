package com.example.pdfreader_kotlin.activities

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pdfreader_kotlin.databinding.ActivityOpenFilePdfBinding
import com.example.pdfreader_kotlin.model.ModelFileItem
import com.github.barteksc.pdfviewer.listener.OnDrawListener
import com.github.barteksc.pdfviewer.util.FitPolicy
import java.io.File


class OpenFilePdfActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOpenFilePdfBinding
    private var file: ModelFileItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenFilePdfBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initView()
        initListener()
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

            binding.pdfViewer.fromFile(file)
                .pages(0, 1, 2, 3, 4, 5) // all pages are displayed by default
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

                .onDrawAll(object : OnDrawListener {
                    override fun onLayerDrawn(
                        canvas: Canvas,
                        pageWidth: Float,
                        pageHeight: Float,
                        displayedPage: Int
                    ) {
                        // Tùy chỉnh thuộc tính dòng kẻ
                        val paint = Paint()
                        paint.color = Color.LTGRAY
                        paint.strokeWidth = 4f // Độ dày của dòng kẻ

                        // Vẽ dòng kẻ ngang ở cuối mỗi trang
                        canvas.drawLine(0f, pageHeight, pageWidth, pageHeight, paint)
                    }
                })

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