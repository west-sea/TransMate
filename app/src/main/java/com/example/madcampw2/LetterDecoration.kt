package com.example.madcampw2

import android.app.Activity
import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.view.View.DragShadowBuilder
import android.view.View.OnDragListener
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream


class LetterDecoration : Activity() {
    private var topLayoutList: MutableList<ImageView> = mutableListOf()
    private var bottomLayoutList: MutableList<ImageView> = mutableListOf()
    private var mImgList: ArrayList<ImageView> = arrayListOf()

    private lateinit var bitmapBase: Bitmap

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letter_decoration)

        //1. 편지 내용 가져오기
        val imageView = findViewById<ImageView>(R.id.image)
        val intent = intent
        if (intent.hasExtra("backgroundImageByteArray")) {
            val byteArray = intent.getByteArrayExtra("backgroundImageByteArray")
            bitmapBase = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
            imageView.setImageBitmap(bitmapBase)
            //Log.v("BaseSize", getBitmapSize(bitmapBase).toString())
        }


        //2. 드래그앤 드롭 기능
        var mImg = findViewById<View>(R.id.image) as ImageView
        mImg.tag = IMAGEVIEW_TAG
        mImgList.add(mImg)

        for (tmpImg in mImgList) {
            tmpImg.setOnLongClickListener(LongClickListener())

            findViewById<FrameLayout>(R.id.toplinear).setOnDragListener(
                DragListener()
            )
            findViewById<FrameLayout>(R.id.bottomlinear).setOnDragListener(
                DragListener()
            )
        }

        // 3. 스티커 추가 기능 구현
        stickerTabChange()
        animalImagePress()
        ribonImagePress()
        shapeImagePress()

        // 4. 저장하기 기능 추가
        gotoLetter3()
    }


    fun gotoLetter3(){
        val btn_save = findViewById<Button>(R.id.btnSave)
        btn_save.setOnClickListener {

            GlobalScope.launch(Dispatchers.Main) {
                val byteArray = withContext(Dispatchers.Default) {
                    for(addstk in topLayoutList){
                        bitmapBase = drawTextOnImage(bitmapBase, addstk)
                    }
                    compressBitmapToByteArray(bitmapBase)
                }
                val intent = Intent(applicationContext, LetterSend::class.java)
                intent.putExtra("finalBitmap", byteArray)
                startActivity(intent)
            }
        }
    }
    private suspend fun drawTextOnImage(resource: Bitmap, stk: ImageView): Bitmap {
        return withContext(Dispatchers.Default) {
            val originalBitmap = resource

            // 이미지 크기 가져오기
            val width = originalBitmap.width
            val height = originalBitmap.height

            // 새로운 비트맵 생성
            val resultBitmap = Bitmap.createBitmap(width, height, originalBitmap.config)


            val canvas = Canvas(resultBitmap)
            canvas.drawBitmap(originalBitmap, 0f, 0f, null)

            val bitmapStk = stk.drawable.toBitmap()

            // Paint 객체 설정
            val paint = Paint().apply {
                color = Color.BLACK // 텍스트 색상
            }

            val x = stk.x*2
            var y = stk.y*2
            canvas.drawBitmap(bitmapStk, x, y, paint)

            resultBitmap
        }
    }
    private suspend fun compressBitmapToByteArray(bitmap: Bitmap): ByteArray {
        return withContext(Dispatchers.Default) {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream)
            byteArrayOutputStream.toByteArray()
        }
    }


    fun stickerTabChange(){
        var btn_ani = findViewById<Button>(R.id.btn_animal)
        var btn_rib=findViewById<Button>(R.id.btn_ribon)
        var btn_sha = findViewById<Button>(R.id.btn_shape)
        var stkLayout1 = findViewById<RelativeLayout>(R.id.bottomLayout)
        var stkLayout2 = findViewById<RelativeLayout>(R.id.bottomLayout2)
        var stkLayout3 = findViewById<RelativeLayout>(R.id.bottomLayout3)
        btn_ani.setOnClickListener{
            stkLayout1.visibility = View.VISIBLE
            stkLayout2.visibility = View.INVISIBLE
            stkLayout3.visibility = View.INVISIBLE
        }
        btn_rib.setOnClickListener{
            stkLayout1.visibility = View.INVISIBLE
            stkLayout2.visibility = View.VISIBLE
            stkLayout3.visibility = View.INVISIBLE
        }
        btn_sha.setOnClickListener{
            stkLayout1.visibility = View.INVISIBLE
            stkLayout2.visibility = View.INVISIBLE
            stkLayout3.visibility = View.VISIBLE
        }
    }

    fun addStickerBtn(stk: Int){
        var newImg = addImageViewWithIcon(stk)
        newImg.tag = IMAGEVIEW_TAG
        newImg.setOnLongClickListener(LongClickListener())
        mImgList.add(newImg)
    }

    private inner class LongClickListener : OnLongClickListener {
        override fun onLongClick(view: View): Boolean {
            val item = ClipData.Item(
                view.tag as CharSequence
            )
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(
                view.tag.toString(),
                mimeTypes, item
            )
            val shadowBuilder = DragShadowBuilder(
                view
            )
            view.startDragAndDrop(
                data,  // data to be dragged
                shadowBuilder,  // drag shadow
                view,  // 드래그 드랍할  Vew
                0 // 필요없는 플래그
            )
            view.visibility = View.INVISIBLE
            return true
        }
    }

    internal inner class DragListener : OnDragListener {
        var normalShape = ContextCompat.getDrawable(this@LetterDecoration, R.color.white)
        var targetShape = ContextCompat.getDrawable(this@LetterDecoration, R.color.drag_fg1)

        override fun onDrag(v: View, event: DragEvent): Boolean {
            when (event.action) {
                DragEvent.ACTION_DRAG_LOCATION -> {
                    val view = event.localState as View
                    view.x = event.x - view.width / 2
                    view.y = event.y - view.height / 2
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    v.background = targetShape
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    v.background = normalShape
                }
                DragEvent.ACTION_DROP -> {
                    if (v === findViewById<View>(R.id.bottomlinear)) {
                        val view = event.localState as View
                        val viewgroup = view.parent as ViewGroup
                        viewgroup.removeView(view)

                        bottomLayoutList.add(view as ImageView)
                        topLayoutList.remove(view as ImageView)

                        val containView = v as FrameLayout
                        containView.addView(view)
                        view.visibility = View.VISIBLE
                    } else if (v === findViewById<View>(R.id.toplinear)) {
                        val view = event.localState as View
                        val viewgroup = view.parent as ViewGroup
                        viewgroup.removeView(view)

                        topLayoutList.add(view as ImageView)
                        bottomLayoutList.remove(view as ImageView)

                        val containView = v as FrameLayout
                        containView.addView(view)
                        view.visibility = View.VISIBLE
                    } else {
                    }
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    v.background = normalShape
                }
                else -> {
                }
            }
            return true
        }
    }

    private fun addImageViewWithIcon(stk: Int): ImageView {
        val iconDrawable: Drawable? = ContextCompat.getDrawable(this, stk)
        val imageView = ImageView(this)
        imageView.setImageDrawable(iconDrawable)
        val layoutParams = FrameLayout.LayoutParams(
            resources.dpToPx(25), // width
            resources.dpToPx(25)  // height
        )
        imageView.layoutParams = layoutParams
        findViewById<FrameLayout>(R.id.bottomlinear).addView(imageView)
        return imageView
    }

    fun Resources.dpToPx(dp: Int): Int {
        return (dp * this.displayMetrics.density).toInt()
    }

    companion object {
        private const val IMAGEVIEW_TAG = "드래그 이미지"
    }



    private fun animalImagePress(){
        findViewById<ImageButton>(R.id.btn1stk1).setOnClickListener {
            addStickerBtn(R.drawable.animal1)
        }
        findViewById<ImageButton>(R.id.btn1stk2).setOnClickListener {
            addStickerBtn(R.drawable.animal2)
        }
        findViewById<ImageButton>(R.id.btn1stk3).setOnClickListener {
            addStickerBtn(R.drawable.animal3)
        }
        findViewById<ImageButton>(R.id.btn1stk4).setOnClickListener {
            addStickerBtn(R.drawable.animal4)
        }
        findViewById<ImageButton>(R.id.btn1stk5).setOnClickListener {
            addStickerBtn(R.drawable.animal5)
        }
        findViewById<ImageButton>(R.id.btn1stk6).setOnClickListener {
            addStickerBtn(R.drawable.animal6)
        }
        findViewById<ImageButton>(R.id.btn1stk7).setOnClickListener {
            addStickerBtn(R.drawable.animal7)
        }
        findViewById<ImageButton>(R.id.btn1stk8).setOnClickListener {
            addStickerBtn(R.drawable.animal8)
        }
    }
    private fun ribonImagePress(){
        findViewById<ImageButton>(R.id.btn2stk1).setOnClickListener {
            addStickerBtn(R.drawable.ribon1)
        }
        findViewById<ImageButton>(R.id.btn2stk2).setOnClickListener {
            addStickerBtn(R.drawable.ribon2)
        }
        findViewById<ImageButton>(R.id.btn2stk3).setOnClickListener {
            addStickerBtn(R.drawable.ribon3)
        }
        findViewById<ImageButton>(R.id.btn2stk4).setOnClickListener {
            addStickerBtn(R.drawable.ribon4)
        }
        findViewById<ImageButton>(R.id.btn2stk5).setOnClickListener {
            addStickerBtn(R.drawable.ribon5)
        }
        findViewById<ImageButton>(R.id.btn2stk6).setOnClickListener {
            addStickerBtn(R.drawable.ribon6)
        }
        findViewById<ImageButton>(R.id.btn2stk7).setOnClickListener {
            addStickerBtn(R.drawable.ribon7)
        }
        findViewById<ImageButton>(R.id.btn2stk8).setOnClickListener {
            addStickerBtn(R.drawable.ribon8)
        }
    }
    private fun shapeImagePress(){
        findViewById<ImageButton>(R.id.btn3stk1).setOnClickListener {
            addStickerBtn(R.drawable.shape1)
        }
        findViewById<ImageButton>(R.id.btn3stk2).setOnClickListener {
            addStickerBtn(R.drawable.shape2)
        }
        findViewById<ImageButton>(R.id.btn3stk3).setOnClickListener {
            addStickerBtn(R.drawable.shape3)
        }
        findViewById<ImageButton>(R.id.btn3stk4).setOnClickListener {
            addStickerBtn(R.drawable.shape4)
        }
        findViewById<ImageButton>(R.id.btn3stk5).setOnClickListener {
            addStickerBtn(R.drawable.shape5)
        }
        findViewById<ImageButton>(R.id.btn3stk6).setOnClickListener {
            addStickerBtn(R.drawable.shape6)
        }
        findViewById<ImageButton>(R.id.btn3stk7).setOnClickListener {
            addStickerBtn(R.drawable.shape7)
        }
        findViewById<ImageButton>(R.id.btn3stk8).setOnClickListener {
            addStickerBtn(R.drawable.shape8)
        }
    }
}

