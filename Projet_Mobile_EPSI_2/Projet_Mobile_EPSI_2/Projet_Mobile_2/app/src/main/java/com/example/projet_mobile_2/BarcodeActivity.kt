package com.example.projet_mobile_2

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.oned.Code128Writer
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.EncodeHintType

fun createBarcode(text: String, format: BarcodeFormat, width: Int, height: Int): BitMatrix? {
    val hints = mutableMapOf<EncodeHintType, Any>()
    hints[EncodeHintType.MARGIN] = 0
    val writer = when (format) {
        BarcodeFormat.QR_CODE -> QRCodeWriter()
        BarcodeFormat.CODE_128 -> Code128Writer()
        else -> throw IllegalArgumentException("Unsupported barcode format")
    }
    return try {
        writer.encode(text, format, width, height, hints)
    } catch (e: WriterException) {
        null
    }
}

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class BarcodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode)

        val text = "12345"
        val format = BarcodeFormat.CODE_128
        val width = 500
        val height = 200
        val barcode = createBarcode(text, format, width, height)
        if (barcode != null) {
            val bitmap = Bitmap.createBitmap(barcode.width, barcode.height, Bitmap.Config.ARGB_8888)
            for (x in 0 until barcode.width) {
                for (y in 0 until barcode.height) {
                    bitmap.setPixel(x, y, if (barcode.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
            val imageView = findViewById<ImageView>(R.id.imageView)
            imageView.setImageBitmap(bitmap)
        }
    }
}

