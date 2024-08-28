package com.example.madcampw2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.madcampw2.letterread.ReceivedLetter
import com.example.madcampw2.letterread.SentLetter

class LetterRead : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letter_read)

        val btnreceived = findViewById<ImageButton>(R.id.btnReceived)
        btnreceived.setOnClickListener {
            val fragreceived = supportFragmentManager.beginTransaction()
            fragreceived.replace(R.id.frameLayout, ReceivedLetter())
            fragreceived.commit()
        }

        val btnsent = findViewById<ImageButton>(R.id.btnSent)
        btnsent.setOnClickListener {
            val fragsent = supportFragmentManager.beginTransaction()
            fragsent.replace(R.id.frameLayout, SentLetter())
            fragsent.commit()
        }
    }
}