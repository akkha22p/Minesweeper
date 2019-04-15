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

    private var paintBackground: Paint = Paint()
    private var paintLine: Paint = Paint()
    private var paintText: Paint = Paint()
    private var paintRect: Paint = Paint()
    private var bitmapBg = BitmapFactory.decodeResource(resources, R.drawable.mabomb)
    private var flag = BitmapFactory.decodeResource(resources, R.drawable.flag)

    private var toDrawI = 0
    private var toDrawJ = 0
    private var over = false
    private var win = false
    private var check = false

    init {

        TicTacToeModel.resetModel()

        paintBackground.color = Color.BLACK

        paintBackground.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 8f

        paintRect.color = Color.GRAY

        paintText.color = Color.BLACK
        paintText.textSize = 10f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        paintText.textSize = height / 8f
        flag = Bitmap.createScaledBitmap(flag, width / 5, height / 5, false)
        bitmapBg = Bitmap.createScaledBitmap(bitmapBg, width / 5, height / 5, false)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)
        drawPlayers(canvas, toDrawI, toDrawJ)
        drawGameBoard(canvas)
    }

    private fun drawGameBoard(canvas: Canvas?) {
        drawBorder(canvas)

        drawLines(canvas)
    }

    private fun drawLines(canvas: Canvas?) {
        canvas?.drawLine((width / 5).toFloat(), 0f, (width / 5).toFloat(), height.toFloat(), paintLine)
        canvas?.drawLine((2 * width / 5).toFloat(), 0f, (2 * width / 5).toFloat(), height.toFloat(), paintLine)
        canvas?.drawLine((3 * width / 5).toFloat(), 0f, (3 * width / 5).toFloat(), height.toFloat(), paintLine)
        canvas?.drawLine((4 * width / 5).toFloat(), 0f, (4 * width / 5).toFloat(), height.toFloat(), paintLine)
        canvas?.drawLine((6 * width / 5).toFloat(), 0f, (6 * width / 5).toFloat(), height.toFloat(), paintLine)
    }

    private fun drawBorder(canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        canvas?.drawLine(0f, (height / 5).toFloat(), width.toFloat(), (height / 5).toFloat(), paintLine)
        canvas?.drawLine(0f, (2 * height / 5).toFloat(), width.toFloat(), (2 * height / 5).toFloat(), paintLine)
        canvas?.drawLine(0f, (3 * height / 5).toFloat(), width.toFloat(), (3 * height / 5).toFloat(), paintLine)
        canvas?.drawLine(0f, (4 * height / 5).toFloat(), width.toFloat(), (4 * height / 5).toFloat(), paintLine)
        canvas?.drawLine(0f, (6 * height / 5).toFloat(), width.toFloat(), (6 * height / 5).toFloat(), paintLine)
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

    private fun drawPlayers(canvas: Canvas?, i: Int, j: Int) {

        for (i in 0..4) {
            for (j in 0..4) {
                if (TicTacToeModel.getFieldContent(i, j).wasClicked) {

                    checkIfBombAndNotFlag(canvas, i, j)
                    checkIfBombAndFlag(canvas, i, j)
                    checkIfEmptyAndFlag(canvas, i, j)
                    checkIfIntAndFlag(canvas, i, j)
                    checkIfIntAndNotFlag(canvas, i, j)
                }
            }
        }
        checkWinning()
    }

    public fun checkWinning() {
        if (TicTacToeModel.checkIfWin()) {
            win = true
            over = true
            Snackbar.make(this,"You Won", Snackbar.LENGTH_LONG).show()
        }
        else if(!TicTacToeModel.checkIfWin() && over){
            Snackbar.make(this,"You Lose", Snackbar.LENGTH_LONG).show()
        }
    }

    public fun checkIfBombAndNotFlag(canvas: Canvas?, i: Int, j: Int) {
        if (TicTacToeModel.getFieldContent(i, j).getTypes() == 0 && TicTacToeModel.getFieldContent(
                i,
                j
            ).getIsFlagged() == false
        ) {
            canvas?.drawBitmap(bitmapBg, (i * width / 5).toFloat(), (j * height / 5).toFloat(), null)
            over = true
            showAllBomb(canvas)
        }
    }

    public fun checkIfBombAndFlag(canvas: Canvas?, i: Int, j: Int) {
        if (TicTacToeModel.getFieldContent(i, j).getTypes() == 0 && TicTacToeModel.getFieldContent(
                i,
                j
            ).getIsFlagged() == true
        ) {
            canvas?.drawBitmap(flag, (i * width / 5).toFloat(), (j * height / 5).toFloat(), null)
        }
    }

    public fun checkIfEmptyAndFlag(canvas: Canvas?, i: Int, j: Int) {
        if (TicTacToeModel.getFieldContent(i, j).getTypes() == -1) {
            //canvas?.drawBitmap(bitmapBg2, (i * width / 5).toFloat(), (j * height / 5).toFloat(), null)
            canvas?.drawRect(
                (i * width / 5).toFloat(),
                (j * height / 5).toFloat(),
                (i * width / 5).toFloat() + (width / 5),
                (j * width / 5).toFloat() + (width / 5),
                paintRect
            )

            when {
                TicTacToeModel.getFieldContent(i, j).getIsFlagged() == true -> over = true
                else -> reveal(canvas, i, j)
            }
        }
    }

    public fun checkIfIntAndFlag(canvas: Canvas?, i: Int, j: Int) {
        if (TicTacToeModel.getFieldContent(i, j).getTypes() != 0 && TicTacToeModel.getFieldContent(
                i,
                j
            ).getIsFlagged() == true
        ) {
            drawInt(canvas, i, j)
            over = true
        }
    }

    public fun checkIfIntAndNotFlag(canvas: Canvas?, i: Int, j: Int) {
        if (TicTacToeModel.getFieldContent(i, j).getTypes() != 0 && TicTacToeModel.getFieldContent(
                i,
                j
            ).getTypes() != -1 && TicTacToeModel.getFieldContent(i, j).getIsFlagged() == false
        ) {
            drawInt(canvas, i, j)
        }
    }

    private fun revealHelper(canvas: Canvas?, i: Int, j: Int) {

        if (i > 4 || i < 0 || j > 4 || j < 0) {
            return
        }
        if (TicTacToeModel.getFieldContent(i, j).getWasClick()) {
            return
        } else if (TicTacToeModel.getFieldContent(i, j).getTypes() == -1) {
            TicTacToeModel.getFieldContent(i, j).wasClicked = true;
            canvas?.drawRect(
                (i * width / 5).toFloat(),
                (j * height / 5).toFloat(),
                (i * width / 5).toFloat() + (width / 5),
                (j * width / 5).toFloat() + (width / 5),
                paintRect
            )
            reveal(canvas, i, j);
        } else if (TicTacToeModel.getFieldContent(i, j).getTypes() != -1) {
            TicTacToeModel.getFieldContent(i, j).wasClicked = true;
            drawInt(canvas, i, j)
        }

    }

    public fun reveal(canvas: Canvas?, i: Int, j: Int) {

        revealHelper(canvas, i - 1, j - 1);

        revealHelper(canvas, i - 1, j);

        revealHelper(canvas, i - 1, j + 1);

        revealHelper(canvas, i + 1, j);

        revealHelper(canvas, i, j - 1);

        revealHelper(canvas, i, j + 1);

        revealHelper(canvas, i + 1, j - 1);

        revealHelper(canvas, i + 1, j + 1);

    }

    private fun showAllBomb(canvas: Canvas?) {
        for (i in 0..4) {
            for (j in 0..4) {
                if (TicTacToeModel.getFieldContent(i, j).getTypes() == 0 && !TicTacToeModel.getFieldContent(
                        i,
                        j
                    ).wasClicked
                ) {
                    canvas?.drawBitmap(bitmapBg, (i * width / 5).toFloat(), (j * height / 5).toFloat(), null)
                }
            }
        }
    }

    public fun getGameStatus(): Boolean{
        return win
    }

    public fun isCheck() {
        check = true
    }

    public fun notCheck() {
        check = false
    }

    private fun drawInt(canvas: Canvas?, i: Int, j: Int) {
        val centerX =
            ((i * width / 5 + width / 5) - (5 + width / 5) + ((width / 5) / 2) - (paintText.textSize / 4)).toFloat()
        val centerY = (j * height / 5 + height / 5) - ((paintText.textSize / 3)).toFloat()

        canvas?.drawRect(
            (i * width / 5).toFloat(),
            (j * height / 5).toFloat(),
            (i * width / 5).toFloat() + (width / 5),
            (j * width / 5).toFloat() + (width / 5),
            paintRect
        )
        canvas?.drawText(TicTacToeModel.getFieldContent(i, j).getTypes().toString(), centerX, centerY, paintText)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = View.MeasureSpec.getSize(widthMeasureSpec)
        val h = View.MeasureSpec.getSize(heightMeasureSpec)
        val d = if (w == 0) h else if (h == 0) w else if (w < h) w else h

        setMeasuredDimension(d, d)
    }

    public fun resetGame() {

        TicTacToeModel.resetModel()
        over = false
        invalidate()
    }
}