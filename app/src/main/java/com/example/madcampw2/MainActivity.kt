package com.example.madcampw2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.madcampw2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGoList.setOnClickListener{
            val intent = Intent(this, MateList::class.java)
            startActivity(intent)
        }

        binding.btnGoWrite.setOnClickListener{
            val intent = Intent(this, MateLetter::class.java)
            startActivity(intent)
        }

        binding.btnGoRead.setOnClickListener{
            val intent = Intent(this, LetterRead::class.java)
            startActivity(intent)
        }

    }

}

