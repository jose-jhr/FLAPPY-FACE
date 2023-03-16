package com.example.flappyface

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import kotlin.math.abs


/**
 * se termino la incersion de los elementos obstaculos en el juego
 * falta la colision
 * falta la animacion de los obstaculos
 */

class ObstaclesFlappy {

    //width obstacles
    private var widthDisplayFlappy = 0
    //height obstacles
    private var heightDisplayFlappy = 0

    //bitmap obstacles up
    private lateinit var bitMapObstaclesUp: Bitmap
    //bitmap obstacles down
    private lateinit var bitMapObstaclesDown: Bitmap

    //resource obstacles image
    private var resourceObstacles = R.mipmap.obs

    //porcentage of the obstacles
    private val PORCENTAGE_OBSTACLE_WIDTH = 0.17f

    //Porcentage min of the obstacles
    private val PORCENTAGE_MIN_OBSTACLE = 0.1f

    //Porcentaje max value
    private val PORCENTAGE_MAX_VALUE = 0.55f

    //size step obstacles in the screen face flappy
    private var stepFlappyFace = 0f

    //size width obstacles
    private var widthObstacles = 0

    //min height obstacles
    private var minHeightObstacles = 0

    //max value obstacles height
    private var maxValueObstacles = 0f

    //velocity obstacles
    private var velocityObstacles = 0f

    //start second obstacle
    private var secondObstacle = false

    //-----------deade
    var dead = true

    //count elements point
    private var countPoint = 0

    //points
    var points = 0



    //position x, up de cero hacia alto pantalla, down alto pantalla - down
    //Position (position, position obstacles up,obstacles down) for number elements
    val arrayObstacles = arrayOf(ObstaclesModel(0f,0f,0f,true),
        ObstaclesModel(0f,0f,0f,true))

    val arrayCorrectValues = arrayOf(ObstaclesModel(0f,0f,0f,true),
        ObstaclesModel(0f,0f,0f,true))


    fun getBitMapObstacle(
        resources: Resources,
        widthDisplayFlappy: Int,
        heightDisplayFlappy: Int,
        stepFlappyFace: Float
    ){
        this.widthObstacles = (widthDisplayFlappy*PORCENTAGE_OBSTACLE_WIDTH).toInt()
        this.widthDisplayFlappy = widthDisplayFlappy
        this.heightDisplayFlappy = heightDisplayFlappy
        bitMapObstaclesUp = BitmapFactory.decodeResource(resources, resourceObstacles)
        bitMapObstaclesUp = Bitmap.createScaledBitmap(bitMapObstaclesUp,
            (widthObstacles), heightDisplayFlappy, false)

        //rotate bitmap
        bitMapObstaclesDown = BitmapFactory.decodeResource(resources, resourceObstacles)
        bitMapObstaclesDown = Bitmap.createScaledBitmap(bitMapObstaclesDown, (widthObstacles)
            , heightDisplayFlappy, false)
        bitMapObstaclesDown = rotateBitmap(bitMapObstaclesDown, 180f)
        //size step obstacles in the screen face flappy
        this.stepFlappyFace = stepFlappyFace
        //min height obstacles
        minHeightObstacles = (heightDisplayFlappy*PORCENTAGE_MIN_OBSTACLE).toInt()
        //max value obstacles height
        maxValueObstacles = (heightDisplayFlappy*PORCENTAGE_MAX_VALUE)
        //velocity obstacles
        velocityObstacles = (widthDisplayFlappy*33)/2000f
    }

    /**
     * rotate bitmap
     */
    private fun rotateBitmap(bitMapObstaclesDown: Bitmap, fl: Float): Bitmap {
        // Create a matrix for the manipulation
        val matrix = Matrix()
        matrix.setRotate(fl)
        val rotateBitmap = Bitmap.createBitmap(bitMapObstaclesDown, 0, 0, bitMapObstaclesDown.width, bitMapObstaclesDown.height, matrix, true)
        return rotateBitmap
    }

    /**
     * animation obstacle move
     */
    fun animationObstacleMove(canvas: Canvas) {
        evalueRandomAndReinitObstacles(0)
        evalueRandomAndReinitObstacles(1)
        //evalueRandomAndReinitObstacles(isObstaclesInScreen,positionXobstacles,0)
        //evalueRandomAndReinitObstacles(isObstacles2InScreen,position2Xobstacles,1)
        //down es el valor total del alto del escenario - heightObstaclesDown
        //up es 0 mas heightObstaclesUp con esto puedo determinar el espacio entre los dos.
        canvas!!.drawBitmap(bitMapObstaclesUp!!, widthDisplayFlappy.toFloat()+arrayObstacles[0].positionX, arrayObstacles[0].downObstacle, null)
        canvas.drawBitmap(bitMapObstaclesDown!!, widthDisplayFlappy+arrayObstacles[0].positionX, arrayObstacles[0].upObstacle, null)
        if (-arrayObstacles[0].positionX>(widthDisplayFlappy/2)+widthObstacles/2) secondObstacle = true
        if (secondObstacle){
            //obstacle two diference half
            canvas!!.drawBitmap(bitMapObstaclesUp!!, widthDisplayFlappy.toFloat()+arrayObstacles[1].positionX, arrayObstacles[1].downObstacle, null)
            canvas.drawBitmap(bitMapObstaclesDown!!, widthDisplayFlappy+arrayObstacles[1].positionX, arrayObstacles[1].upObstacle, null)
            if (!dead)arrayObstacles[1].positionX-=velocityObstacles
        }
        if (!dead)arrayObstacles[0].positionX-=velocityObstacles
    //
    }

    private fun evalueRandomAndReinitObstacles(position: Int) {
        if (arrayObstacles[position].endScreen) randomHeightObstacles(position)
        if (widthDisplayFlappy+arrayObstacles[position].positionX+widthObstacles<0){
            arrayObstacles[position].endScreen = true
        }
    }

    private fun randomHeightObstacles(position: Int) {
        //if obstacle end view so obstacle random large
        if (arrayObstacles[position].endScreen){
            randomElements(position)
            arrayObstacles[position].endScreen = false
        }
    }


    /**
     * random elements once
     */
    private fun randomElements(position: Int) {
        val values_heigh = ((0..maxValueObstacles.toInt()).random()).toFloat()
        val positive = abs(values_heigh)
        arrayObstacles[position].upObstacle = (minHeightObstacles+values_heigh-heightDisplayFlappy)
        arrayObstacles[position].downObstacle = heightDisplayFlappy-(maxValueObstacles-positive)-minHeightObstacles
        arrayObstacles[position].positionX = 0f
        if (countPoint>1){
            points +=1
        }
        countPoint++
    }


    /**
     * restart values of obstacles
     */
    fun restartObstacle(){
        arrayObstacles.forEach {
            it.upObstacle = 0f
            it.downObstacle = 0f
            it.endScreen = true
            it.positionX = 0f
        }
        countPoint = 0
        points = 0
        secondObstacle = false
    }

    fun getPositionObstacle() = arrayObstacles

    //get with obstacle
    fun getWidthObstacle() = widthObstacles



}