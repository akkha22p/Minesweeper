package hu.ait.tictactoe

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import hu.ait.tictactoe.view.TicTacToeView
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_main.*

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        btnRestart.setOnClickListener {
            toggleButton.isChecked = false
            tictactoeView.resetGame()
        }

        toggleButton?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                tictactoeView.isCheck()
            } else {
                tictactoeView.notCheck()
            }
        }

    }

}
