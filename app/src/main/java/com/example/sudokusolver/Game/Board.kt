package com.example.sudokusolver.Game

class Board(val size:Int,val cells:List<List<Cell>>) {
    fun getCell(row:Int,col:Int):Cell= cells[row][col]
}