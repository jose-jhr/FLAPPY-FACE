package com.example.flappyface

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas

class RoadFlappy() {

    //bitmap of the image
    private lateinit var bitMapRoad: Bitmap

    //resource road image
    private var resourceRoad = R.mipmap.base

    //move the image
    private var xImage = 0f

    //width display
    private var widthDisplay = 0

    //res value to animation
    private val resValue = 5f

    //get the bitmap of the road
    fun getBitMapRoad(resources:Resources,widthRoad:Int,heightRoad: Int):Bitmap{
        this.widthDisplay = widthRoad
        bitMapRoad = BitmapFactory.decodeResource(resources, resourceRoad)
        bitMapRoad = Bitmap.createScaledBitmap(bitMapRoad, widthRoad, heightRoad, false)
        return bitMapRoad
    }

    /**
     * animation road move
     */
    fun animationRoadMove(canvas:Canvas,roadHeightPosition:Float) {
        canvas!!.drawBitmap(bitMapRoad!!, xImage, roadHeightPosition, null)
        canvas.drawBitmap(bitMapRoad!!, xImage + widthDisplay, roadHeightPosition, null)
        xImage-=resValue
        if (xImage < -widthDisplay) {
            xImage = 0f
        }
    }




}