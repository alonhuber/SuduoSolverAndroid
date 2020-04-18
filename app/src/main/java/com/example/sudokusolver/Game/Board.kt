package com.example.sudokusolver.Game

class Board(val size:Int,var cells:List<List<Cell>>) {

    lateinit var upsideList : List<List<Cell>>
    lateinit var areaList : List<List<List<Cell>>>
    init{
        setCachedLists()
    }
    fun getCell(row:Int,col:Int):Cell= cells[row][col]

    fun setCell(row:Int,col:Int,value:Int){
        cells[row][col].value=value
        upsideList[col][row].value=value
        areaList[row/3][col/3][row/3+col%3].value=value
    }

    fun setCachedLists(){
        upsideList= List(9 ) { i->List(9) { j-> Cell(j,i, getCell(j,i).value) }}
        areaList =List(3 ) { i->List(3) { j->List(9){
                val row:Int=i*3+it/3
                val col:Int=j*3+(it%3)
                Cell(row,col,getCell(row,col).value)
        }}}
    }
}