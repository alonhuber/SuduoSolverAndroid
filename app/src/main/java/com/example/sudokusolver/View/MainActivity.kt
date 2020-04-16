package com.example.sudokusolver.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sudokusolver.R
import com.example.sudokusolver.ViewModel.PlaySudukoViewModel
import kotlinx.android.synthetic.main.activity_main.*
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
    }

    private fun updateSelectedCellUI(cell:Pair<Int,Int>?)=cell?.let{
        SudukoBoardView.updateSelectedCellUI(cell.first,cell.second)
    }

    override fun onTouched(row:Int,col:Int){
        viewModel.sudukoGame.updateSelectedCell(row,col)
    }
}
