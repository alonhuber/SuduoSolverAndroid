package com.example.sudokusolver.Game

class Cell(val row:Int,val colmun:Int,var value:Int) {

    var validNumbers:MutableList<Int> = (1..9).toMutableList()
    fun isHasValue() : Boolean = value!=0
    fun removeValidNumber(value:Int){
        if(validNumbers.contains(value)){
            validNumbers.remove(value)
            if(validNumbers.size==1){
                this.value=validNumbers[0]
            }
        }
    }
    fun resetValidNumber(){
        validNumbers = (1..9).toMutableList()
    }
}