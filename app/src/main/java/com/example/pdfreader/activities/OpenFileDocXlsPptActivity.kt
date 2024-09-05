package com.example.pdfreader.activities

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pdfreader.model.ModelFileItem
import com.example.pdfreaderkotlin.databinding.ActivityOpenFileDocXlsPptBinding
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xslf.usermodel.XMLSlideShow
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFParagraph
import java.io.File
import java.io.FileInputStream

class OpenFileDocXlsPptActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOpenFileDocXlsPptBinding
    private var file: ModelFileItem? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenFileDocXlsPptBinding.inflate(LayoutInflater.from(this))
        initData()
        initView()
        initListener()
        setContentView(binding.root)
    }

    private fun initData() {
        file = intent.getParcelableExtra("data")


    }

    private fun initView() {
        if (file == null) {
            Toast.makeText(this, "file không tồn tại", Toast.LENGTH_SHORT).show()
        } else {

            when {
                file!!.type.contains("doc") || file!!.type.contains("docx") -> openDocFile(file!!)
                file!!.type.contains("xls") || file!!.type.contains("xlsx") -> openXlsFile(file!!)
                file!!.type.contains("ppt") || file!!.type.contains("pptx") -> openPptFile(file!!)
            }
        }


    }

    private fun initListener() {

    }

    //doc

    private fun openDocFile(files: ModelFileItem) {
        val file = File(files.path)
        val fileInputStream = FileInputStream(file)
        val doc = XWPFDocument(fileInputStream)
        val spannableString = SpannableString(getDocumentText(doc))

//        val text = doc.paragraphs.joinToString("\n") { it.text }
        binding.textView.text = spannableString
    }

    private fun getDocumentText(doc: XWPFDocument): String {
        val sb = StringBuilder()
        for (para in doc.paragraphs) {
            sb.append(getParagraphText(para))
            sb.append("\n")
        }
        return sb.toString()
    }

    private fun getParagraphText(paragraph: XWPFParagraph): String {
        val sb = StringBuilder()
        paragraph.runs.forEach { run ->
            val text = run.text() ?: ""
            val color = run.getColor() // lấy màu nê có
            val isBold = run.isBold
            val isItalic = run.isItalic

            // Thêm định dạng ở đây
            val spannable = SpannableString(text)
            if (isBold) {
                spannable.setSpan(StyleSpan(Typeface.BOLD), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            if (isItalic) {
                spannable.setSpan(StyleSpan(Typeface.ITALIC), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            if (color != null) {
                spannable.setSpan(ForegroundColorSpan(Color.parseColor("#${color}")), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            sb.append(spannable)
        }
        return sb.toString()
    }









    private fun openXlsFile(files: ModelFileItem) {

        val file = File(files.path)
        val fileInputStream = FileInputStream(file)
        val workbook = WorkbookFactory.create(fileInputStream)
        val sheet = workbook.getSheetAt(0)

        val text = sheet.iterator().asSequence().joinToString("\n") { row ->
            row.iterator().asSequence().joinToString("\t") { cell ->
                cell.toString()
            }
        }
        binding.textView.text = text
    }

    private fun openPptFile(files: ModelFileItem) {

        val file = File(files.path)
        val fileInputStream = FileInputStream(file)
        val ppt = XMLSlideShow(fileInputStream)

        val text = ppt.slides.joinToString("\n\n") { slide ->
            slide.shapes.joinToString("\n") { shape ->
                shape.toString()
            }
        }
        binding.textView.text = text
    }


}