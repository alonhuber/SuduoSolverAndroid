package com.example.sudokusolver.View

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sudokusolver.Game.Cell
import com.example.sudokusolver.R
import com.example.sudokusolver.ViewModel.PlaySudukoViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import layout.SudukoBoardView

class MainActivity : AppCompatActivity(),SudukoBoardView.OnTouchListener {
    private  lateinit var viewModel:PlaySudukoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SudukoBoardView.registerListener(this)
        val factory: ViewModelProvider.Factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this,factory).get(PlaySudukoViewModel::class.java)
        viewModel.sudukoGame.selectedCellLiveData.observe(this, Observer { updateSelectedCellUI(it) })
        viewModel.sudukoGame.cellsLiveData.observe(this, Observer { updateCells(it) })
        viewModel.sudukoGame.solveSucceessLiveData.observe(this, Observer { updateSolveSuccess(it) })
        val buttons = listOf(oneButton,twoButton,threeButton,fourButton,fiveButton,
            sixButton,sevenButton,eightButton,nineButton)
        buttons.forEachIndexed{ index, button ->
            button.setOnClickListener { viewModel.sudukoGame.handleInput(index + 1) }
        }
        Solve.setOnClickListener{viewModel.sudukoGame.handleSolve()}
        Delete.setOnClickListener{viewModel.sudukoGame.handleDelete()}
        Reset.setOnClickListener{viewModel.sudukoGame.reset()}
    }

    private  fun updateCells(cells:List<List<Cell>>?)=cells?.let {
        SudukoBoardView.updateCell(cells)
    }

    private fun updateSelectedCellUI(cell:Pair<Int,Int>?)=cell?.let{
        SudukoBoardView.updateSelectedCellUI(cell.first,cell.second)
    }
    private fun updateSolveSuccess(isSuccess:Boolean)=isSuccess?.let {
        val builder=AlertDialog.Builder(this)
        builder.setTitle("Solver finish")
        if(isSuccess){
            builder.setMessage("Succeed")
        }else{
            builder.setMessage("Failed")
        }
        builder.setPositiveButton("Close") { dialogInterface:DialogInterface, i:Int-> }
        builder.show()
    }
    override fun onTouched(row:Int,col:Int){
        viewModel.sudukoGame.updateSelectedCell(row,col)
    }
}
