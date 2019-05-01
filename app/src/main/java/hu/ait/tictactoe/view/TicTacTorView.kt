package hu.ait.tictactoe.view

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.support.design.widget.Snackbar
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import hu.ait.tictactoe.model.TicTacToeModel
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import hu.ait.tictactoe.GameActivity
import hu.ait.tictactoe.MainActivity
import hu.ait.tictactoe.R
import hu.ait.tictactoe.model.Field
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_game.view.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*


class TicTacToeView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var intBackground: Paint = Paint()
    private var paintRect: Paint = Paint()
    private var paintText: Paint = Paint()
    private var bombBackground: Paint = Paint()
    private var bomb = BitmapFactory.decodeResource(resources, R.drawable.mabomb)
    private var flag = BitmapFactory.decodeResource(resources, R.drawable.flag)
    private var field = BitmapFactory.decodeResource(resources, R.drawable.field)

    private var over = false
    private var win = false
    private var check = false

    init {

        TicTacToeModel.setModel()

        intBackground.color = Color.GRAY
        intBackground.style = Paint.Style.FILL

        paintRect.color = Color.GRAY
        paintRect.style = Paint.Style.FILL

        bombBackground.color = Color.RED
        bombBackground.style = Paint.Style.FILL

        paintText.color = Color.BLACK
        paintText.textSize = 9f

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        paintText.textSize = height / 8f
        flag = Bitmap.createScaledBitmap(flag, (width / 5)-1, (width / 5)-1, false)
        bomb = Bitmap.createScaledBitmap(bomb, (width / 5)-1, (height / 5)-1, false)
        field = Bitmap.createScaledBitmap(field, width / 5, height / 5, false)
    }

    override fun onDraw(canvas: Canvas?) {
        drawField(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event?.action == MotionEvent.ACTION_DOWN && !over) {
            val tX = event.x.toInt() / (width / 5)
            val tY = event.y.toInt() / (height / 5)

            if (tX < 5 && tY < 5) {
                TicTacToeModel.getFieldContent(tX, tY).wasClicked = true
                if (check) {
                    TicTacToeModel.getFieldContent(tX, tY).setIsFlagged(true)

                }
            }
        }
        invalidate()
        return true
    }

    private fun drawField(canvas: Canvas?) {

        for (i in 0..4) {
            for (j in 0..4) {
                if (TicTacToeModel.getFieldContent(i, j).wasClicked) {
                    decideWhatToDrawIfClicked(canvas, i, j)
                }
                else{
                    canvas?.drawBitmap(field, (i * width / 5).toFloat(), (j * height / 5).toFloat(), null)
                }
            }
        }
        checkForWin()
    }

    private fun decideWhatToDrawIfClicked(canvas: Canvas?, i: Int, j: Int){
        checkIfBombAndNotFlag(canvas, i, j)
        checkIfBombAndFlag(canvas, i, j)
        checkIfEmptyAndFlag(canvas, i, j)
        checkIfIntAndFlag(canvas, i, j)
        checkIfIntAndNotFlag(canvas, i, j)
    }

    private fun drawBomb(canvas: Canvas?, i: Int, j: Int){
        canvas?.drawRect(
            (i * width / 5).toFloat(),
            (j * height / 5).toFloat(),
            (i * width / 5).toFloat() + (width / 5),
            (j * width / 5).toFloat() + (width / 5),
            bombBackground
        )
        canvas?.drawBitmap(bomb, (i * width / 5).toFloat(), (j * height / 5).toFloat(), null)
    }

    private fun checkIfBombAndNotFlag(canvas: Canvas?, i: Int, j: Int) {
        if (TicTacToeModel.getFieldContent(i, j).getTypes() == 0 && TicTacToeModel.getFieldContent(
                i,
                j
            ).getIsFlagged() == false
        ) {
            drawBomb(canvas, i, j)
            over = true
            showAllBombs(canvas)
        }
    }

    private fun checkIfBombAndFlag(canvas: Canvas?, i: Int, j: Int) {
        if (TicTacToeModel.getFieldContent(i, j).getTypes() == 0 && TicTacToeModel.getFieldContent(
                i,
                j
            ).getIsFlagged() == true
        ) {
            canvas?.drawBitmap(flag, (i * width / 5).toFloat(), (j * height / 5).toFloat(), null)
        }
    }

    private fun checkIfEmptyAndFlag(canvas: Canvas?, i: Int, j: Int) {
        if (TicTacToeModel.getFieldContent(i, j).getTypes() == -1) {
            canvas?.drawRect(
                (i * width / 5).toFloat()+1,
                (j * height / 5).toFloat()+1,
                (i * width / 5).toFloat() + (width / 5)-1,
                (j * width / 5).toFloat() + (width / 5)-1,
                paintRect
            )

            when {
                TicTacToeModel.getFieldContent(i, j).getIsFlagged() == true -> over = true
                else -> reveal(canvas, i, j)
            }
        }
    }

    private fun checkIfIntAndFlag(canvas: Canvas?, i: Int, j: Int) {
        if (TicTacToeModel.getFieldContent(i, j).getTypes() != 0 && TicTacToeModel.getFieldContent(i, j).getIsFlagged() == true
        ) {
            drawIntOnField(canvas, i, j)
            over = true
        }
    }

    private fun checkIfIntAndNotFlag(canvas: Canvas?, i: Int, j: Int) {
        if (TicTacToeModel.getFieldContent(i, j).getTypes() != 0 && TicTacToeModel.getFieldContent(
                i,
                j
            ).getTypes() != -1 && TicTacToeModel.getFieldContent(i, j).getIsFlagged() == false
        ) {
            drawIntOnField(canvas, i, j)
        }
    }

    private fun checkForWin() {
        if (TicTacToeModel.checkIfWin()) {
            win = true
            over = true
            Snackbar.make(this,"You Won", Snackbar.LENGTH_LONG).show()
        }
        else if(!TicTacToeModel.checkIfWin() && over){
            Snackbar.make(this,"You Lose", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun revealHelper(canvas: Canvas?, i: Int, j: Int) {

        //Return if out of game board
        if (i > 4 || i < 0 || j > 4 || j < 0) {
            return
        }

        when {
            //Return if the field was already clicked
            TicTacToeModel.getFieldContent(i, j).getWasClick() -> return

            //Reveal empty fields aroud, if field's type is -1
            TicTacToeModel.getFieldContent(i, j).getTypes() == -1 -> {
                TicTacToeModel.getFieldContent(i, j).wasClicked = true
                canvas?.drawRect(
                    (i * width / 5).toFloat()+1,
                    (j * height / 5).toFloat()+1,
                    (i * width / 5).toFloat() + (width / 5)-1,
                    (j * width / 5).toFloat() + (width / 5)-1,
                    paintRect
                )
                reveal(canvas, i, j)
            }

            //Draw number on the selected field, if field's type is not -1 and not a bomb
            TicTacToeModel.getFieldContent(i, j).getTypes() != -1 -> {
                TicTacToeModel.getFieldContent(i, j).wasClicked = true
                drawIntOnField(canvas, i, j)
            }
        }

    }

    private fun reveal(canvas: Canvas?, i: Int, j: Int) {

        revealHelper(canvas, i - 1, j - 1);

        revealHelper(canvas, i - 1, j);

        revealHelper(canvas, i - 1, j + 1);

        revealHelper(canvas, i + 1, j);

        revealHelper(canvas, i, j - 1);

        revealHelper(canvas, i, j + 1);

        revealHelper(canvas, i + 1, j - 1);

        revealHelper(canvas, i + 1, j + 1);

    }

    private fun showAllBombs(canvas: Canvas?) {
        for (i in 0..4) {
            for (j in 0..4) {
                if (TicTacToeModel.getFieldContent(i, j).getTypes() == 0 && !TicTacToeModel.getFieldContent(
                        i,
                        j
                    ).wasClicked
                ) {
                    drawBomb(canvas, i, j)
                }
            }
        }
    }

    private fun drawIntOnField(canvas: Canvas?, i: Int, j: Int) {
        val centerX =
            ((i * width / 5 + width / 5) - (5 + width / 5) + ((width / 5) / 2) - (paintText.textSize / 4))
        val centerY = (j * height / 5 + height / 5) - ((paintText.textSize / 3))

        canvas?.drawRect(
            (i * width / 5).toFloat()+1,
            (j * height / 5).toFloat()+1,
            (i * width / 5).toFloat() + (width / 5)-1,
            (j * width / 5).toFloat() + (width / 5)-1,
            intBackground
        )
        canvas?.drawText(TicTacToeModel.getFieldContent(i, j).getTypes().toString(), centerX, centerY, paintText)
    }

    public fun setGame() {

        TicTacToeModel.setModel()
        over = false
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = View.MeasureSpec.getSize(widthMeasureSpec)
        val h = View.MeasureSpec.getSize(heightMeasureSpec)
        val d = if (w == 0) h else if (h == 0) w else if (w < h) w else h

        setMeasuredDimension(d, d)
    }

    public fun isCheck() {
        check = true
    }

    public fun notCheck() {
        check = false
    }

}