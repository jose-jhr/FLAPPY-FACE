package com.example.flappyface

import android.content.Context
import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent
import android.view.View

class GameFlappyFace(context: Context?) : View(context), ClassEndUp {

    lateinit var endAnimationListener:ClassEndUp

    companion object{
        var widthDisplay = 0
        var heightDisplay = 0
    }

    //porcentage images
    private var PORCE_ROAD = 0.2
    private var PORCE_BACKGROUND = 0.8
    private var PORCE_FACE = 0.1


    //Touch pressed display
    private var touchPressed = false
    //Touch pressed ok confirm
    private var touchPressedOk = false



    //---------------------road---------------------------
    //Road Object flappy
    private var roadObjectFlappy = RoadFlappy()

    //position height of the image
    private var heightRoad = 0

    //position init road image
    private var roadHeightStartPosition = 0f

    //---------------------background---------------------
    //background Object flappy
    var backgroundObjectFlappy = BackgroundScene()

    //position init background image
    private var backgroundHeight = 0

    //---------------------Face---------------------------
    //bird Object flappy
    var faceObjectFlappy = FaceFlappy()

    private var faceWidth = 0

    private var faceHeight = 0

    private var stepFlappyFace = 0f

    //---------------------Obstacles---------------------------
    //obstacles Object flappy
    var obstaclesObjectFlappy = ObstaclesFlappy()

    //------------------- Collision obstacles -----------------
    val detectCollisionObstacles = CollisionEvaluate()

    //with obstacle
    private var withObstacle = 0

    //-------------------DEAD--------------------
    var dead = true

    //------------  pause or start game -----\
    var pause = true

    //load listener status
    private lateinit var listenerGame:GameStatus

    //listener point
    private lateinit var listenerPoint: PointListener


    //set the width and height of the display
    fun setWidthHeight(width: Int, height: Int) {
        widthDisplay = width
        heightDisplay = height
        //get porcentaje
        porcentageImgs()
        //get bitmaps of the images
        convertDrawableToBitmap()
        //init interface
        initInterface()
    }

    /**
     * init interface
     */
    private fun initInterface() {
        faceObjectFlappy.setAnimationListener(this)
    }

    /**
     * porcentaje images
     */
    private fun porcentageImgs() {
        heightRoad = (heightDisplay*PORCE_ROAD).toInt()
        //road position
        roadHeightStartPosition = (heightDisplay*(1-PORCE_ROAD).toFloat())
        //background position end
        backgroundHeight = (heightDisplay*PORCE_BACKGROUND).toInt()
        //face width
        faceHeight = (widthDisplay*PORCE_FACE).toInt()
        //face height
        faceWidth = faceHeight+faceHeight/3
        //set height road
        faceObjectFlappy.positionHeightRoad = roadHeightStartPosition
        //set step flappy face
        stepFlappyFace = (backgroundHeight*.1).toFloat()
    }

    /**
     * init road Image with objet roadObjectFlappy
     */
    fun convertDrawableToBitmap() {
        //init road image send width and height image with porcentage of the display
        roadObjectFlappy.getBitMapRoad(resources,widthDisplay,heightRoad)
        //init background image send width and height image with porcentage of the display
        backgroundObjectFlappy.getBitmapBackground(resources,widthDisplay,backgroundHeight)
        //init face image send width and height image with porcentage of the display
        faceObjectFlappy.getBitmapFaceFlappy(resources,faceWidth,faceHeight,backgroundHeight,stepFlappyFace)
        //init obstacles image send width and height image with porcentage of the display
        obstaclesObjectFlappy.getBitMapObstacle(resources, widthDisplay,backgroundHeight,stepFlappyFace)
        //get width obstacle
        withObstacle = obstaclesObjectFlappy.getWidthObstacle()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (widthDisplay != 0 && heightDisplay != 0) {
            //animation background move
            backgroundObjectFlappy.imageBackground(canvas!!,0f, BackgroundScene.BackgroundType.DAY)
            //obstacle move
            obstaclesObjectFlappy.animationObstacleMove(canvas!!)
            //animation road move
            roadObjectFlappy.animationRoadMove(canvas!!,roadHeightStartPosition)
            //animation face move
            faceObjectFlappy.drawFaceFlappy(canvas!!)
            //if preseed touch
            if (touchPressedOk){
                faceObjectFlappy.upFaceFlappy()
            }
            //colision evaluate
            if(detectCollisionObstacles.evaluateCollision(obstaclesObjectFlappy.getPositionObstacle(),
                    faceObjectFlappy.getPositionYFaceBird(),backgroundHeight,faceWidth,withObstacle,
                    widthDisplay)){
                println("  ----------------GAME OVER ---------------------")
                dead = true
                obstaclesObjectFlappy.dead = true
            }

            listenerPoint.pointMore(obstaclesObjectFlappy.points)

        }

        if (!pause) {
            android.os.Handler().postDelayed({
                    if (!dead) {
                        postInvalidateOnAnimation()
                    } else {
                        if (!faceObjectFlappy.evalueEndGameFaceBelow()) {
                            postInvalidateOnAnimation()
                        }else{
                            listenerGame.endGame()
                        }
                    }
            }, 33)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (!dead){
            if (event!!.action == MotionEvent.ACTION_DOWN) {
                touchPressed = false
                faceObjectFlappy.upFaceFlappy()
                Log.d("touchvalue2023","touch pressed")
            }

            if (event!!.action == MotionEvent.ACTION_UP) {
                touchPressedOk = false
                touchPressed = false
                Log.d("touchvalue2023","touch released")
            }
        }
        return true

    }

    /**
     * end up animation face
     */
    override fun endUp(endAnimation: Boolean) {
        if (!touchPressedOk){
            touchPressedOk = endAnimation && touchPressed
        }
    }

    /**
     * play game
     */
    fun playGame(){
        dead = false
        obstaclesObjectFlappy.dead = false
        obstaclesObjectFlappy.restartObstacle()
        faceObjectFlappy.reinitFaceFlappy(backgroundHeight)
        faceObjectFlappy.pause = false
        faceObjectFlappy.start()
        pause = false
        postInvalidateOnAnimation()
    }

    /**
     * init listener
     */
    fun setGameStatusListener(listener:GameStatus){
        this.listenerGame = listener
    }

    fun setPointListener(listener: PointListener){
        this.listenerPoint = listener
    }


}
