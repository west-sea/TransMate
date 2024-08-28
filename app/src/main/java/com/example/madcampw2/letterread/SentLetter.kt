package com.example.madcampw2.letterread

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import android.widget.*
import com.example.madcampw2.MyGlobals
import com.example.madcampw2.R



class SentLetter : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sent_letter, container, false)

        val fragBinding = view.findViewById<GridView>(R.id.gridView)
        val gAdapter = MyGridAdapter(requireContext())

        fragBinding.adapter = gAdapter

        return view
    }

    inner class MyGridAdapter(private val context: Context) : BaseAdapter() {
        val retrievedBitmap: Bitmap? = MyGlobals.getInstance().getBitImg()
        private var picID = arrayListOf<Int>(
            R.drawable.icon_ex

        )

        override fun getCount(): Int {
            return picID.size
        }

        override fun getItem(i: Int): Any? {
            return null
        }

        override fun getItemId(i: Int): Long {
            return 0
        }

        override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
            val imageView = ImageView(context)
            imageView.layoutParams = ViewGroup.LayoutParams(300, 300)
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            imageView.setPadding(5, 5, 5, 5)

            imageView.setImageBitmap(retrievedBitmap)

            // 이미지를 선택했을 때 큰 화면으로 보기
            imageView.setOnClickListener {
                makeBigImage(i)
            }

            return imageView
        }

        private fun makeBigImage(i: Int) {
            val dialogView = View.inflate(context, R.layout.activity_bigimage, null)
            val dlg = AlertDialog.Builder(context)
            val ivPic = dialogView.findViewById<ImageView>(R.id.bigimage)
            ivPic.setImageBitmap(retrievedBitmap)
            dlg.setTitle("큰 이미지")
            dlg.setIcon(R.drawable.ic_launcher_foreground)
            dlg.setView(dialogView)
            dlg.setNegativeButton("닫기", null)
            dlg.show()
        }
    }
}