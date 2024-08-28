package com.example.madcampw2

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView


class LetterSend : AppCompatActivity() {
    private lateinit var bitmapBase: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letter_send)

        val imageView = findViewById<ImageView>(R.id.finalImage)
        val intent = intent
        if (intent.hasExtra("finalBitmap")) {
            val byteArray = intent.getByteArrayExtra("finalBitmap")
            bitmapBase = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
            MyGlobals.getInstance().bitImg = bitmapBase
            imageView.setImageBitmap(bitmapBase)
        }

        val buttonUpload = findViewById<Button>(R.id.buttonUpload)
        buttonUpload.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}