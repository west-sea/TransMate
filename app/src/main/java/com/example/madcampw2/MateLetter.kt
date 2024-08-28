package com.example.madcampw2


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MateLetter : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mate_letter)

        var letter_type = R.drawable.letter
        val editText = findViewById<EditText>(R.id.writeLetterTextView)
        val linearLayout = findViewById<LinearLayout>(R.id.bottomLayout)

        val btn_type1 = findViewById<Button>(R.id.type1)
        val btn_type2 = findViewById<Button>(R.id.type2)
        val btn_type3 = findViewById<Button>(R.id.type3)
        val btn_type4 = findViewById<Button>(R.id.type4)

        btn_type1.setOnClickListener{
            letter_type = R.drawable.letter
            val newDrawable: Drawable? = ContextCompat.getDrawable(this, letter_type)
            linearLayout.background = newDrawable
        }
        btn_type2.setOnClickListener{
            letter_type = R.drawable.letter3
            val newDrawable: Drawable? = ContextCompat.getDrawable(this, letter_type)
            linearLayout.background = newDrawable
        }
        btn_type3.setOnClickListener{
            letter_type = R.drawable.letter4
            val newDrawable: Drawable? = ContextCompat.getDrawable(this, letter_type)
            linearLayout.background = newDrawable
        }
        btn_type4.setOnClickListener{
            letter_type = R.drawable.letter5
            val newDrawable: Drawable? = ContextCompat.getDrawable(this, letter_type)
            linearLayout.background = newDrawable
        }

        val letterButton = findViewById<Button>(R.id.letterButton)
        letterButton.setOnClickListener {
            // 비동기 작업을 실행
            GlobalScope.launch(Dispatchers.Main) {
                val byteArray = withContext(Dispatchers.Default) {
                    val bitmap = drawTextOnImage(letter_type, editText.text.toString())
                    compressBitmapToByteArray(bitmap)
                }

                // 다음 액티비티 호출
                val intent = Intent(applicationContext, LetterDecoration::class.java)
                intent.putExtra("backgroundImageByteArray", byteArray)
                startActivity(intent)
            }
        }
    }

    private suspend fun drawTextOnImage(resourceId: Int, text: String): Bitmap {
        return withContext(Dispatchers.Default) {
            val originalBitmap = BitmapFactory.decodeResource(resources, resourceId)

            // 이미지 크기 가져오기
            val width = originalBitmap.width
            val height = originalBitmap.height

            // 새로운 비트맵 생성
            val resultBitmap = Bitmap.createBitmap(width, height, originalBitmap.config)

            // Canvas를 사용하여 이미지에 텍스트 그리기
            val canvas = Canvas(resultBitmap)
            canvas.drawBitmap(originalBitmap, 0f, 0f, null)

            // Paint 객체 설정
            val paint = Paint().apply {
                color = Color.BLACK // 텍스트 색상
                textSize = 30f// 텍스트 크기
            }

            val textLines = text.split("\n")
            val x = 200f
            var y = 300f

            for (line in textLines) {
                canvas.drawText(line, x, y, paint)
                y += 47f
            }

            resultBitmap
        }
    }

    private suspend fun compressBitmapToByteArray(bitmap: Bitmap): ByteArray {
        return withContext(Dispatchers.Default) {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream)
            byteArrayOutputStream.toByteArray()
        }
    }
}
