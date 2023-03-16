package com.example.flappyface

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.flappyface.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var gameFlappyFace: GameFlappyFace

    //binding
    lateinit var binding: ActivityMainBinding
    //add view one
    var layoutAddQuestion = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //https://github.com/samuelcust/flappy-bird-assets/blob/master/sprites/base.png
        //color bar
        window.statusBarColor = ContextCompat.getColor(this, R.color.green)

        binding.play.setOnClickListener {
            gameFlappyFace.playGame()
            binding.play.visibility = View.GONE
        }



    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (!layoutAddQuestion) {
            layoutAddQuestion = true
            addGame()
        }
    }

    /**
     * add game
     */
    private fun addGame() {
        gameFlappyFace = GameFlappyFace(this)
        gameFlappyFace.setWidthHeight(window.decorView.width,
            window.decorView.height)



        //add the game to the layout
        binding.containerGame.addView(gameFlappyFace)

        //add textview
        val txtPoint = TextView(this).apply {
            text = "1 PUNTO"
            setTextColor(ContextCompat.getColor(this@MainActivity, R.color.yellow))
            textSize = 15f
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                addRule(RelativeLayout.ALIGN_PARENT_END)
                setMargins(0, 5, 5, 0)
            }
        }
        binding.containerGame.addView(txtPoint)

        //listener game
        gameFlappyFace.setGameStatusListener(object:GameStatus{
            override fun endGame() {
                binding.play.visibility = View.VISIBLE
                binding.txtPoint.visibility = View.VISIBLE
            }
        })

        gameFlappyFace.setPointListener(object :PointListener{
            override fun pointMore(point: Int) {
                txtPoint.text = point.toString()+" Puntos"
            }
        })



    }

}