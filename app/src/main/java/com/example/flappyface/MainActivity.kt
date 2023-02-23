package com.example.flappyface

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }

}