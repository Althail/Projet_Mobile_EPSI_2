package com.example.projet_mobile_2

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import java.util.*

class BarcodeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode)

        val barcode = generateRandomBarcode()
        val imgBarcode: ImageView = findViewById<ImageView>(R.id.img_barcode)


//        imgBarcode.setImageBitmap(barcode)
    }


    fun generateRandomBarcode(): BitMatrix {
        // Génération d'un code aléatoire de 12 chiffres
        val random = Random()
        val barcodeNumber = (1..12).map { random.nextInt(10) }.joinToString("")

        // Configuration de l'encodage du code-barres
        val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
        hints[EncodeHintType.MARGIN] = 0 // Pas de marge

        try {
            // Encodage du code-barres avec le format EAN_13
            val writer = MultiFormatWriter()
            val bitMatrix = writer.encode(barcodeNumber, BarcodeFormat.EAN_13, 600, 300, hints)
            return bitMatrix
        } catch (e: WriterException) {
            // Gestion des erreurs d'encodage
            throw RuntimeException("Erreur lors de la génération du code-barres", e)
        }

    }

}
