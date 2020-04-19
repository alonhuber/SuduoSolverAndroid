package com.example.sudokusolver.Game

import androidx.lifecycle.MutableLiveData
import com.example.sudokusolver.Game.Solving.Solver

class SudukoGame {
    var selectedCellLiveData = MutableLiveData<Pair<Int,Int>>()
    var cellsLiveData =MutableLiveData<List<List<Cell>>>()
    var solveSucceessLiveData =MutableLiveData<Boolean>()

    private var selectedRow=-1
    private var selectedColumn=-1

    private lateinit var board:Board
    init{
        reset()
    }

    fun reset(){
        val cells= List(9 ) { i->List(9) { j-> Cell(i, j, 0) }}
        board= Board(9,cells)
        selectedCellLiveData.postValue(Pair(selectedRow,selectedColumn))
        cellsLiveData.postValue(cells)
    }


    fun handleInput(number:Int){
        if(selectedRow==-1||selectedColumn==-1)return

        board.getCell(selectedRow,selectedColumn).value=number
        cellsLiveData.postValue(board.cells)
    }

    fun handleSolve(){
        board.resetValidNumber()
        val solver:Solver= Solver()
        val(b,s)=solver.solveSudoku(board)
        board=b
        cellsLiveData.postValue(board.cells)
        //solveSucceessLiveData.postValue(s)
    }

    fun handleDelete(){
        if(selectedRow==-1||selectedColumn==-1)return
        board.getCell(selectedRow,selectedColumn).value=0
        cellsLiveData.postValue(board.cells)
    }

    fun updateSelectedCell(row: Int,col:Int){
        selectedRow=row
        selectedColumn=col
        selectedCellLiveData.postValue(Pair(selectedRow,selectedColumn))
    }
}
