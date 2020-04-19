package com.example.sudokusolver.Game.Solving

import com.example.sudokusolver.Game.Board

class Solver() {
    var lastMissingNumbers:Int=0
    enum class Status{
        Succeeded,
        Stuck,
        Progress
    }
    fun solveSudoku(originalBoard: Board):Pair<Board,Boolean>{
        var board= originalBoard
        board.setCachedLists()

        while (true)
        {
            when(getSolvingStatus(board)){
                Status.Succeeded->return Pair(board,true)
                Status.Stuck->return Pair(board,false)

            }
            board=solveWithCellsRemover(board)
            board=solveWithGroupRemover(board)
        }
    }

    private fun solveWithGroupRemover(board: Board): Board{
        val removerByGroup = RemoveValidByGroup()
        var newBoard:Board=board
        newBoard.cells.forEach {
            newBoard=removerByGroup.removeValidByGroup(it, newBoard)
        }

        newBoard.upsideList.forEach {
            newBoard=removerByGroup.removeValidByGroup(it, newBoard)
        }

        newBoard.areaList.forEach {
            it.forEach {
                newBoard=removerByGroup.removeValidByGroup(it, newBoard)
            }
        }
        return newBoard
    }

    private fun solveWithCellsRemover(board: Board): Board{

        val removeValidBySquare=RemoveValidBySquare()
        lateinit var newBoard:Board
        board.cells.forEach{list->
            list.forEach{it->
                newBoard=removeValidBySquare.removeValidByCell(it,board,board.cells[it.row],
                    board.upsideList[it.colmun],board.areaList[it.row/3][it.colmun/3])
            }
        }
        return newBoard
    }

    fun getSolvingStatus(board: Board):Status{
        var missingNumbers:Int=0
        board.cells.forEach{
            it.forEach{
                if(!it.isHasValue()){
                    missingNumbers++
                }
            }
        }
        val status: Status
        status = when (missingNumbers) {
            0 -> Status.Succeeded
            lastMissingNumbers -> Status.Stuck
            else -> Status.Progress
        }
        lastMissingNumbers=missingNumbers
        return status
    }
}