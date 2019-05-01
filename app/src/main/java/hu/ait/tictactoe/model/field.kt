package hu.ait.tictactoe.model

import android.support.annotation.Nullable

data class Field(
    var type: Int, var minesAround: Int,
    var isFlagged: Boolean, var wasClicked: Boolean) {

    //Type- bomb or number
    fun setTypes(newType: Int) {
        type = newType
    }

    fun getTypes(): Int? = type


    fun setIsFlagged(flag: Boolean) {
        isFlagged = flag
    }

    fun getIsFlagged(): Boolean? = isFlagged

    fun wasClicked(click: Boolean) {
        wasClicked = click
    }

    fun getWasClick(): Boolean = wasClicked

}
