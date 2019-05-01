package hu.ait.tictactoe.model

import android.support.annotation.Nullable
import android.util.Log
import java.util.*
import kotlin.experimental.and

//Use 'object' to make it a singletonL object
object TicTacToeModel {

    private var countBomb = 0

    @Nullable  private val fieldMatrix: Array<Array<Field>> = Array(5, { Array(5, { Field(10, 5, true, false)}) })

//    @Nullable private val fieldMatrix: Array<Array<Field>> = arrayOf(
//        arrayOf(
//            Field(10, 5, true, false),
//            Field(10, 6, false, false),
//            Field(10, 6, false, false),
//            Field(10, 8, false, false),
//            Field(10, 8, false, false)
//        ),
//        arrayOf(
//            Field(10, 5, true, false),
//            Field(10, 8, false, false),
//            Field(10, 8, false, false),
//            Field(10, 8, false, false),
//            Field(10, 8, false, false)
//        ),
//        arrayOf(
//            Field(10, 5, true, false),
//            Field(10, 8, false, false),
//            Field(10, 8, false, false),
//            Field(10, 8, false, false),
//            Field(10, 8, false, false)
//        ),
//        arrayOf(
//            Field(10, 5, true, false),
//            Field(10, 8, false, false),
//            Field(10, 8, false, false),
//            Field(10, 8, false, false),
//            Field(10, 8, false, false)
//        ),
//        arrayOf(
//            Field(10, 5, true, false),
//            Field(10, 8, false, false),
//            Field(10, 8, false, false),
//            Field(10, 8, false, false),
//            Field(10, 8, false, false)
//        )
//    )

    fun setModel(){
        (0..4).forEach { i ->
            (0..4).forEach { j ->
                getFieldContent(i, j).setTypes(-10)
                getFieldContent(i, j).setIsFlagged(false)
                getFieldContent(i, j).wasClicked = false
            }
        }
        generateBomb()
        generateField()
    }

    private fun generateBomb() {
        countBomb = 0
        val random = Random(System.currentTimeMillis())
        var randomI: Int
        var randomJ: Int
        (0..2).forEach {
            randomI = random.nextInt(4)
            randomJ = random.nextInt(4)

            if(getFieldContent(randomI, randomJ).getTypes() != 0){
                getFieldContent(randomI, randomJ).setTypes(0)
                countBomb++
            }
        }

    }

    private fun generateMinesAroundNonBorderField(i: Int, j: Int): Int {
        var countBomb = 0

        if (getFieldContent(i, j).getTypes() == 0) {
            return -1
        }

        if (getFieldContent(i + 1, j).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i - 1, j).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i, j - 1).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i, j + 1).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i + 1, j + 1).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i - 1, j - 1).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i + 1, j - 1).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i - 1, j + 1).getTypes() == 0) {
            countBomb++
        }

        return countBomb
    }

    private fun generateMinesAroundN0Field(i: Int, j: Int): Int {
        var countBomb = 0

        if (getFieldContent(i - 1, j).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i, j + 1).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i - 1, j + 1).getTypes() == 0) {
            countBomb++
        }
        return countBomb
    }

    private fun generateMinesAround0NField(i: Int, j: Int): Int {
        var countBomb = 0
        if (getFieldContent(i, j).getTypes() == 0) {
            return -1
        }

        if (getFieldContent(i + 1, j).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i, j - 1).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i + 1, j - 1).getTypes() == 0) {
            countBomb++
        }

        return countBomb
    }

    private fun generateMinesAroundI0Field(i: Int, j: Int): Int {
        var countBomb = 0
        if (getFieldContent(i, j).getTypes() == 0) {
            return -1
        }

        if (getFieldContent(i + 1, j).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i, j - 1).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i, j + 1).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i + 1, j + 1).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i + 1, j - 1).getTypes() == 0) {
            countBomb++
        }

        return countBomb
    }

    private fun generateMinesAroundINField(i: Int, j: Int): Int {
        var countBomb = 0
        if (getFieldContent(i, j).getTypes() == 0) {
            return -1
        }

        if (getFieldContent(i - 1, j).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i, j - 1).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i, j + 1).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i - 1, j - 1).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i - 1, j + 1).getTypes() == 0) {
            countBomb++
        }

        return countBomb
    }

    private fun generateMinesAroundJ0Field(i: Int, j: Int): Int {
        var countBomb = 0
        if (getFieldContent(i, j).getTypes() == 0) {
            return -1
        }

        if (getFieldContent(i + 1, j).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i - 1, j).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i, j + 1).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i + 1, j + 1).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i - 1, j + 1).getTypes() == 0) {
            countBomb++
        }

        return countBomb
    }

    private fun generateMinesAroundJNField(i: Int, j: Int): Int {
        var countBomb = 0
        if (getFieldContent(i, j).getTypes() == 0) {
            return -1
        }

        if (getFieldContent(i + 1, j).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i - 1, j).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i, j - 1).getTypes() == 0) {
            countBomb++
        }


        if (getFieldContent(i - 1, j - 1).getTypes() == 0) {
            countBomb++
        }

        if (getFieldContent(i + 1, j - 1).getTypes() == 0) {
            countBomb++
        }

        return countBomb
    }

    private fun generateMinesAround00Field(): Int {
        var countBomb = 0

        if (getFieldContent(0, 0).getTypes() == 0) {
            return -1
        }
        if (getFieldContent(1, 0).getTypes() == 0) {
            countBomb++
        }
        if (getFieldContent(0, 1).getTypes() == 0) {
            countBomb++
        }
        if (getFieldContent(1, 1).getTypes() == 0) {
            countBomb++
        }
        return countBomb
    }

    fun getNNNeighbor(): Int {
        var countBomb = 0
        if (getFieldContent(4, 4).getTypes() == 0) {
            return -1
        }
        if (getFieldContent(3, 4).getTypes() == 0) {
            countBomb++
        }
        if (getFieldContent(4, 3).getTypes() == 0) {
            countBomb++
        }
        if (getFieldContent(3, 3).getTypes() == 0) {
            countBomb++
        }
        return countBomb
    }

    private fun generateField() {
        for (i in 0..4) {
            for (j in 0..4) {
                var bomb = 0
                when {
                    i != 0 && i != 4 && j != 0 && j != 4 -> bomb = generateMinesAroundNonBorderField(i, j)
                    i == 0 && j == 0 -> bomb = generateMinesAround00Field()
                    i == 4 && j == 4 -> bomb = getNNNeighbor()
                    i == 0 && j == 4 -> bomb = generateMinesAround0NField(i, j)
                    i == 4 && j == 0 -> bomb = generateMinesAroundN0Field(i, j)
                    j == 0 -> bomb = generateMinesAroundJ0Field(i, j)
                    j == 4 -> bomb = generateMinesAroundJNField(i, j)
                    i == 0 -> bomb = generateMinesAroundI0Field(i, j)
                    i == 4 -> bomb = generateMinesAroundINField(i, j)
                }

                when (bomb) {
                    0 -> getFieldContent(i, j).setTypes(-1)
                    -1 -> getFieldContent(i, j).setTypes(0)
                    else -> getFieldContent(i, j).setTypes(bomb)
                }
            }
        }
    }

    fun getFieldContent(x: Int, y: Int) = fieldMatrix[x][y]

    fun checkIfWin(): Boolean{
        var countCurrentBomb = 0
        (0..4).forEach { i ->
            (0..4).forEach { j ->
                if(getFieldContent(i, j).getTypes() == 0 && getFieldContent(i, j).isFlagged && getFieldContent(i, j).wasClicked){
                    countCurrentBomb++
                }
            }
        }
        if(countCurrentBomb == countBomb) return true

        return false
    }
}