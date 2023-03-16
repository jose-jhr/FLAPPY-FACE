package com.example.flappyface

import android.widget.TextView

class CollisionEvaluate {

    //down es el valor total del alto del escenario - heightObstaclesDown
    //up es 0 mas heightObstaclesUp con esto puedo determinar el espacio entre los dos.

    fun evaluateCollision(
        arrayObstacles: Array<ObstaclesModel>,
        arrayPosFaceBird: Array<PositionFaceBirdModel>,
        backgroundHeight: Int,
        faceWidth: Int,
        withObstacle: Int,
        widthDisplay: Int
    ):Boolean{
        var collision = false

            for (obs in arrayObstacles){
                //si el obstaculo sobrepasa al facebird + width y si
                if (arrayPosFaceBird[0].positionXBird+faceWidth>widthDisplay+obs.positionX &&
                    arrayPosFaceBird[0].positionXBird<widthDisplay+obs.positionX+withObstacle){
                    if (arrayPosFaceBird[0].positionYBird>obs.downObstacle || arrayPosFaceBird[0].positionYBird<obs.upObstacle+backgroundHeight){
                        println("muerto----")
                        collision = true
                        break
                    }
                }
            }

            //println("posx bird ${arrayPosFaceBird[0].positionXBird+faceWidth}  posx obs ${widthDisplay+arrayObstacles[0].positionX}")
           // println("posy bird ${arrayPosFaceBird[0].positionYBird+faceWidth}  pos y obs ${arrayObstacles[0].upObstacle+backgroundHeight}")


        return collision

    }


}