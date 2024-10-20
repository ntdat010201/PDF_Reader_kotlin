package com.example.pdfreader_kotlin.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.pdfreader_kotlin.databinding.ActivityOpenFileDocXlsPptBinding
import com.example.pdfreader_kotlin.model.ModelFileItem
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xslf.usermodel.XMLSlideShow
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.FileInputStream

class OpenFileDocXlsPptActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOpenFileDocXlsPptBinding
    private var file: ModelFileItem? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenFileDocXlsPptBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
//        initData()
//        initView()
//        initListener()
        file = intent.getParcelableExtra("data")
        Log.d("DAT", "onCreate: ${file?.path}")

        if (file!!.type.contains("doc") || file!!.type.contains("docx")) {
            readWordFile(file!!.path)
        } else if (file!!.type.contains("xls") || file!!.type.contains("xlsx")) {
            readExcelFile(file!!.path)
        } else if(file!!.type.contains("ppt") || file!!.type.contains("pptx")){
            readPptFile(file!!.path)
        }

    }

    private fun readWordFile(filePath: String) {
        try {
            val fileInputStream = FileInputStream(filePath)
            val document = XWPFDocument(fileInputStream)

            val stringBuilder = StringBuilder()
            for (paragraph in document.paragraphs) {
                stringBuilder.append(paragraph.text).append("\n")
            }

            binding.textView.text = stringBuilder.toString()
            document.close()
            fileInputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            binding.textView.text = "Error: ${e.message}"
        }
    }

    private fun readExcelFile(filePath: String) {
        try {
            val fileInputStream = FileInputStream(filePath)
            val workbook = WorkbookFactory.create(fileInputStream)
            val sheet = workbook.getSheetAt(0)

            val stringBuilder = StringBuilder()
            for (row in sheet) {
                for (cell in row) {
                    stringBuilder.append(cell.toString()).append("\t")
                }
                stringBuilder.append("\n")
            }
            binding.textView.text = stringBuilder.toString() // Cập nhật TextView
            workbook.close()
            fileInputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            binding.textView.text = "Error: ${e.message}"
        }
    }

    private fun readPptFile(filePath: String) {
        try {
            val fileInputStream = FileInputStream(filePath)
            val ppt = XMLSlideShow(fileInputStream)

            val stringBuilder = StringBuilder()
            for (slide in ppt.slides) {
                stringBuilder.append("Slide: ${slide.slideNumber}\n")
                slide.shapes.forEach { shape ->
                    if (shape is org.apache.poi.xslf.usermodel.XSLFTextShape) {
                        stringBuilder.append(shape.text).append("\n")
                    }
                }
                stringBuilder.append("\n") // Thêm dòng trống giữa các slide
            }

            binding.textView.text = stringBuilder.toString()
            ppt.close()
            fileInputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            binding.textView.text = "Error: ${e.message}"
        }
    }

}