package com.example.sudokusolver.Game.Solving

import com.example.sudokusolver.Game.Board
import com.example.sudokusolver.Game.Cell

class RemoveValidBySquare {
    fun removeValidByCell(
        cell: Cell, originalBoard: Board,
        line: List<Cell>, row: List<Cell>, area: List<Cell>): Board {
        var board = originalBoard
        var updateCell=cell
        if (!cell.isHasValue()) {
            updateCell = getGroupInfo(updateCell, line)
            updateCell = getGroupInfo(updateCell,row)
            updateCell =getGroupInfo(updateCell,area)
            board.setCell(updateCell.row,updateCell.colmun,updateCell.value)
        }

        return board
    }

    private fun getGroupInfo(cell: Cell, group: List<Cell>): Cell {
        if(!cell.isHasValue()) {
            group.forEach {
                cell.validNumbers.remove(it.value)
                if (cell.validNumbers.size == 1) {
                    cell.value = cell.validNumbers[0]
                    return cell
                }
            }
        }

        return cell
    }
}

