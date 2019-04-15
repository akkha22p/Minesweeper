package hu.ait.tictactoe.model

import android.support.annotation.Nullable

data class Field(
    var type: Int, var minesAround: Int,
    var isFlagged: Boolean, var wasClicked: Boolean) {


    public fun setTypes(newType: Int) {
        type = newType
    }

    public fun getTypes(): Int? = type


    public fun setIsFlagged(flag: Boolean) {
        isFlagged = flag
    }

    public fun getIsFlagged(): Boolean? = isFlagged

    public fun wasClicked(click: Boolean) {
        wasClicked = click
    }

    public fun getWasClick(): Boolean = wasClicked

}
