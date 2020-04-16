package com.example.sudokusolver.Game

import androidx.lifecycle.MutableLiveData

class SudukoGame {
    var selectedCellLiveData = MutableLiveData<Pair<Int,Int>>()
    private var selectedRow=-1
    private var selectedColumn=-1
    init{
        selectedCellLiveData.postValue(Pair(selectedRow,selectedColumn))
    }

    fun updateSelectedCell(row: Int,col:Int){
        selectedRow=row
        selectedColumn=col
        selectedCellLiveData.postValue(Pair(selectedRow,selectedColumn))
    }
}
