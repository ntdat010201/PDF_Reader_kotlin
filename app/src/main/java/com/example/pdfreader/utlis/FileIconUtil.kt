package com.example.pdfreader.utlis

import com.example.pdfreaderkotlin.R


class FileIconUtil {
    companion object {
        fun getFileIcon(mimeType: String): Int {
            return when {
                mimeType.contains("pdf") -> R.drawable.ic_pdf
                mimeType.contains("doc") || mimeType.contains("docx") -> R.drawable.ic_doc
                mimeType.contains("xls") || mimeType.contains("xlsx") -> R.drawable.ic_xls
                mimeType.contains("ppt") || mimeType.contains("pptx") -> R.drawable.ic_ppt
                else -> R.drawable.ic_pdf // Hình ảnh mặc định nếu không xác định được loại file
            }
        }
    }
}