package com.example.sudokusolver.ViewModel

import androidx.lifecycle.ViewModel
import com.example.sudokusolver.Game.SudukoGame

class PlaySudukoViewModel :ViewModel(){
    val sudukoGame = SudukoGame()
}