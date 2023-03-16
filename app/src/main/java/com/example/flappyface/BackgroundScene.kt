package com.example.flappyface

import android.animation.ValueAnimator
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint


class BackgroundScene {

    enum class BackgroundType {
        DAY,NIGHT,THREE
    }

    //background available
    private var backgroundDay = R.mipmap.fondo_dos
    private var backgroundNight = R.mipmap.background_night

    //bitmap of the image
    private lateinit var bitMapBackgroundDay: Bitmap
    private lateinit var bitMapBackgroundNight: Bitmap

    //value for animation no repeat
    private var animationDayOrNigth = 0

    //paint
    private val paint = Paint()
    //alpha animation paint
    private var alphaBackground = 0

    //width display
    private var widthDisplay = 0

    //true and false change background
    private var changeBackground = BackgroundType.DAY


    //get the bitmap of the road
    fun getBitmapBackground(resources: Resources, widthBackground:Int, heightBackground: Int): Bitmap {
        this.widthDisplay = widthBackground
        bitMapBackgroundDay = BitmapFactory.decodeResource(resources, backgroundDay)
        bitMapBackgroundDay = Bitmap.createScaledBitmap(bitMapBackgroundDay, widthBackground, heightBackground, false)

        bitMapBackgroundNight = BitmapFactory.decodeResource(resources, backgroundNight)
        bitMapBackgroundNight = Bitmap.createScaledBitmap(bitMapBackgroundNight, widthBackground, heightBackground, false)

        return bitMapBackgroundDay
    }

    fun imageBackground(canvas: Canvas, backgroundHeightPosition: Float, typeBackground: BackgroundType) {

        if(typeBackground == BackgroundType.DAY){
            if (changeBackground == BackgroundType.NIGHT){
                animateValueAlphaChange()
                changeBackground = BackgroundType.DAY
            }else{
                canvas!!.drawBitmap(bitMapBackgroundDay!!, 0f, backgroundHeightPosition, paint)
            }
        }else{
            if (changeBackground == BackgroundType.DAY){
                animateValueAlphaChange()
                changeBackground = BackgroundType.NIGHT
            }else{
                canvas!!.drawBitmap(bitMapBackgroundNight!!, 0f, backgroundHeightPosition, paint)
            }
        }


    }

    /**
     * animation change background
     */
    private fun animateValueAlphaChange() {
            ValueAnimator.ofInt(0, 255).apply {
                duration = 500
                addUpdateListener {
                    alphaBackground = it.animatedValue as Int
                    println("------------------- alphaBackground: $alphaBackground")
                    paint.alpha = alphaBackground
                }

                start()
            }
    }

}