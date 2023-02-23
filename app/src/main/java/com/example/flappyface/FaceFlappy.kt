package com.example.flappyface

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas

class FaceFlappy {

    lateinit var endAnimationListener:ClassEndUp

    private var widthFace = 0
    private var heightFace = 0

    private var positionFaceX = 0f
    private var positionFaceY = 0f

    private var faceFlappy = arrayListOf(R.mipmap.bird1, R.mipmap.bird2, R.mipmap.bird3)
    //face flappy index
    private var faceFlappyIndex = 0

    //bitmap of the image
    private lateinit var bitMapFaceFlappy: ArrayList<Bitmap>

    //time change face flappy
    private var timeChangeFaceFlappy = 80
    //time init change face flappy
    private var timeInitChangeFaceFlappy = 0L
    //descend face flappy
    private var descendAscendFaceFlappy = false

    //position road
    var positionHeightRoad = 0f

    //value animator
    lateinit var valueAnimator: ValueAnimator

    //load value up
    private var loadValueUp = true

    //gravity
    private var gravity = 10f

    //time functon parabolic
    private var timeParabolic = 0f




    /**
     * get bitmap face flappy
     */
    fun getBitmapFaceFlappy(
        resources: Resources,
        widthFace: Int,
        heightFace: Int,
        backgroundHeight: Int
    ){
        this.widthFace = widthFace
        this.heightFace = heightFace
        bitMapFaceFlappy = arrayListOf()
        //init face flappy bitmaps
        for (i in faceFlappy) {
            bitMapFaceFlappy.add(BitmapFactory.decodeResource(resources, i))
            bitMapFaceFlappy[faceFlappyIndex] = Bitmap.createScaledBitmap(bitMapFaceFlappy[faceFlappyIndex], widthFace, heightFace, false)
            faceFlappyIndex++
        }
        //init index
        faceFlappyIndex = 0

        //position initial x and y
        positionFaceX = ((GameFlappyFace.widthDisplay/4)*1.5).toFloat()
        positionFaceY = ((backgroundHeight/2).toFloat())
        //time init change face flappy
        timeInitChangeFaceFlappy = System.currentTimeMillis()
    }

    /**
     * draw face flappy primary function
     */
    fun drawFaceFlappy(canvas: Canvas){
        testColisionFaceWithRoad()
        canvas!!.drawBitmap(bitMapFaceFlappy[faceFlappyIndex],positionFaceX , positionFaceY, null)
        if (loadValueUp)downFaceFlappy()
        evalueChangeFaceFlappy()
    }

    /**
     * down gravity face flappy
     */
    fun downFaceFlappy(){
        //rotate face flappy bitMapFaceFlappy[faceFlappyIndex].rotate(10f)
        //positionFaceY += 25
        positionFaceY += gravityFaceFlappy()
    //valueAnimatorFaceFlappyPositionFaceY(10f)
    }

    /**
     * gravity face flappy
     */
    fun gravityFaceFlappy(): Float {
        timeParabolic += (System.currentTimeMillis() - timeInitChangeFaceFlappy)/600f
        //rotate face flappy bitMapFaceFlappy[faceFlappyIndex].rotate(10f)
        return 0.5f*gravity*timeParabolic*timeParabolic

    }

    /**
     * jump face flappy
     */
    fun upFaceFlappy(){
        timeInitChangeFaceFlappy = System.currentTimeMillis()
        timeParabolic = 1f
        //positionFaceY -= 120
        valueAnimatorFaceFlappyPositionFaceY(-180f+gravityFaceFlappy())
    }

    /**
     * value animator face flappy position Face Y
     */
    fun valueAnimatorFaceFlappyPositionFaceY(valueObjective:Float){
        loadValueUp = false
        sendEndAnimation(loadValueUp)
        valueAnimator = ValueAnimator.ofFloat(positionFaceY, positionFaceY+valueObjective)
        valueAnimator.duration = 100// milliseconds
        valueAnimator.addUpdateListener { animation ->
            positionFaceY = animation.animatedValue as Float
        }
        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation:Animator) {
                loadValueUp = true
                sendEndAnimation(loadValueUp)
            }
        })
        valueAnimator.start()
    }


    /**
     * test colision face with road
     */
    private fun testColisionFaceWithRoad() {
        if(positionFaceY+heightFace > positionHeightRoad){
            positionFaceY = positionHeightRoad-heightFace
        }
    }


    /**
     * evalue and  change face flappy index to animation sprites
     */
    private fun evalueChangeFaceFlappy() {
        if(System.currentTimeMillis() - timeInitChangeFaceFlappy >= timeChangeFaceFlappy.toLong()){
            timeInitChangeFaceFlappy = System.currentTimeMillis()
            if (!descendAscendFaceFlappy){
                faceFlappyIndex++
                if(faceFlappyIndex >= bitMapFaceFlappy.size){
                    faceFlappyIndex = bitMapFaceFlappy.size-1
                    descendAscendFaceFlappy = true
                }
            }else{
                faceFlappyIndex--
                if(faceFlappyIndex < 0){
                    faceFlappyIndex = 0
                    descendAscendFaceFlappy = false
                }
            }
        }
    }

    /**
     * end animation listener
     */
    fun setAnimationListener(endAnimationListener: ClassEndUp){
        this.endAnimationListener = endAnimationListener
    }

    /**
     * send end animation
     */
    private fun sendEndAnimation(endAnimation:Boolean){
        endAnimationListener.endUp(endAnimation)
    }



}