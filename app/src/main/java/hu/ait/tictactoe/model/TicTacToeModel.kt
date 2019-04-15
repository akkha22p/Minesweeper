package hu.ait.tictactoe.model

import android.support.annotation.Nullable
import android.util.Log
import java.util.*
import kotlin.experimental.and

//Use 'object' to make it a singletonL just one object
object TicTacToeModel {


    private var countBomb = 0
    @Nullable private val fieldMatrix: Array<Array<Field>> = arrayOf(
        arrayOf(
            Field(10, 5, true, false),
            Field(10, 6, false, false),
            Field(10, 6, false, false),
            Field(10, 8, false, false),
            Field(10, 8, false, false)
        ),
        arrayOf(
            Field(10, 5, true, false),
            Field(10, 8, false, false),
            Field(10, 8, false, false),
            Field(10, 8, false, false),
            Field(10, 8, false, false)
        ),
        arrayOf(
            Field(10, 5, true, false),
            Field(10, 8, false, false),
            Field(10, 8, false, false),
            Field(10, 8, false, false),
            Field(10, 8, false, false)
        ),
        arrayOf(
            Field(10, 5, true, false),
            Field(10, 8, false, false),
            Field(10, 8, false, false),
            Field(10, 8, false, false),
            Field(10, 8, false, false)
        ),
        arrayOf(
            Field(10, 5, true, false),
            Field(10, 8, false, false),
            Field(10, 8, false, false),
            Field(10, 8, false, false),
            Field(10, 8, false, false)
        )
    )

    private fun generateBomb() {
        countBomb = 0
        val random = Random(System.currentTimeMillis())
        var randomI = 0
        var randomJ = 0
        for (i in 0..2) {
            randomI = random.nextInt(4)
            randomJ = random.nextInt(4)

            if(getFieldContent(randomI, randomJ).getTypes() != 0){
                getFieldContent(randomI, randomJ).setTypes(0)
                countBomb++
            }
        }

    }

    public fun getCountBomb(): Int{
        return countBomb
    }

    public fun getNeighbor(i: Int, j: Int): Int {
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

    private fun getN0Neighbor(i: Int, j: Int): Int {
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

    private fun get0NNeighbor(i: Int, j: Int): Int {
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

    private fun getI0Neighbor(i: Int, j: Int): Int {
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

    private fun getINNeighbor(i: Int, j: Int): Int {
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

    private fun getJ0Neighbor(i: Int, j: Int): Int {
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

    private fun getJNNeighbor(i: Int, j: Int): Int {
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

    private fun get00Neighbor(): Int {
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

    public fun getNNNeighbor(): Int {
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
                if (i != 0 && i != 4 && j != 0 && j != 4) {
                    bomb = getNeighbor(i, j)
                } else if (i == 0 && j == 0) {
                    bomb = get00Neighbor()
                } else if (i == 4 && j == 4) {
                    bomb = getNNNeighbor()
                } else if (i == 0 && j == 4) {
                    bomb = get0NNeighbor(i, j)
                } else if (i == 4 && j == 0) {
                    bomb = getN0Neighbor(i, j)
                } else if (j == 0) {
                    bomb = getJ0Neighbor(i, j)
                } else if (j == 4) {
                    bomb = getJNNeighbor(i, j)
                } else if (i == 0) {
                    bomb = getI0Neighbor(i, j)
                } else if (i == 4) {
                    bomb = getINNeighbor(i, j)
                }

                if (bomb == 0) {
                    getFieldContent(i, j).setTypes(-1)
                } else if (bomb == -1) {
                    getFieldContent(i, j).setTypes(0)
                } else {
                    getFieldContent(i, j).setTypes(bomb)
                }
            }
        }
    }

    public fun getFieldContent(x: Int, y: Int) = fieldMatrix[x][y]


    public fun resetModel(){
        for(i in 0..4){
            for(j in 0..4){
                getFieldContent(i, j).setTypes(-10)
                getFieldContent(i, j).setIsFlagged(false)
                getFieldContent(i, j).wasClicked = false
            }
        }
        generateBomb()
        generateField()
    }

    public fun checkIfWin(): Boolean{
        var countCurrentBomb = 0
        for(i in 0..4){
            for(j in 0..4){
                if(getFieldContent(i, j).getTypes() == 0 && getFieldContent(i, j).isFlagged && getFieldContent(i, j).wasClicked){
                    countCurrentBomb++
                }
            }
        }
        if(countCurrentBomb == countBomb){
            return true
        }

        return false
    }
}