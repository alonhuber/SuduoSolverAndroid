package com.example.sudokusolver.Game.Solving

import com.example.sudokusolver.Game.Board
import com.example.sudokusolver.Game.Cell

class RemoveValidByGroup {

    fun removeValidByGroup(group:List<Cell>,board: Board):Board{

        var validValueToCell= mutableMapOf<Int,Pair<Int,Int>>()
        group.forEach{ it ->
            var cell=board.getCell(it.row,it.colmun)
            if (!cell.isHasValue())
            {
                cell.validNumbers.forEach{validNumber->
                    //If the value is found more then once it don't had one square
                    if (validValueToCell.contains(validNumber))
                        validValueToCell[validNumber]=Pair(-1,-1)
                    else
                        validValueToCell[validNumber] = Pair(cell.row,cell.colmun)
                }
            }
            else{
                validValueToCell[cell.value]=Pair(-1,-1)
            }

        }
        validValueToCell.forEach{
            if(it.value!=Pair(-1,-1)){
                board.setCell(it.value.first,it.value.second,it.key)
            }
        }
        return board
    }
}