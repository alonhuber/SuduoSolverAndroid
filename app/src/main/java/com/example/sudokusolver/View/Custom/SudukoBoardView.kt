package layout
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.sudokusolver.Game.Cell
//import com.patrickfeltes.sudokuyoutube.game.Cell

class SudukoBoardView(context: Context,attributeSet: AttributeSet) :View(context,attributeSet)
{
    private var sqrtSize=3
    private var size=9
    private var cellSizePixel =0F
    private var selectedRow=-1
    private var selectedColumn=-1
    private var cells :List<List<Cell>>?=null
    private  var listener: SudukoBoardView.OnTouchListener?=null

    private val thickLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 4F
    }
    private val thinLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 2F
    }
    private val selectedCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#6ead3a")
    }
    private val conflictingCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#efedef")
    }
    private val textPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
        textSize=24f
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixels =Math.min(widthMeasureSpec,heightMeasureSpec)
        setMeasuredDimension(sizePixels,sizePixels)
    }

    override fun onDraw(canvas : Canvas){
        cellSizePixel=(width/size).toFloat()
        fillCells(canvas)
        drawLines(canvas)
        drawText(canvas)
    }

    private fun fillCells(canvas: Canvas){
        cells?.forEach{ it ->
            it?.forEach{cell->
                val c=cell.colmun
                val r=cell.row
                if (r == selectedRow && c == selectedColumn) {
                    fillCell(canvas, r, c, selectedCellPaint)
                } else if (r == selectedRow || c == selectedColumn) {
                    fillCell(canvas, r, c, conflictingCellPaint)
                } else if (r / sqrtSize == selectedRow / sqrtSize && c / sqrtSize == selectedColumn / sqrtSize) {
                    fillCell(canvas, r, c, conflictingCellPaint)
                }
            }
        }
    }

    private  fun fillCell(canvas: Canvas, row: Int,column: Int,paint: Paint){
        canvas.drawRect(column*cellSizePixel,row*cellSizePixel,(column+1)*cellSizePixel,(row+1)*cellSizePixel,paint)
    }
    private fun drawLines(canvas: Canvas){
        canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), thickLinePaint)
        for(i in 1 until size){
            val paintToUse= when(i%sqrtSize){
                0->thickLinePaint
                else-> thinLinePaint
            }
            canvas.drawLine(i*cellSizePixel,0F,
                i*cellSizePixel,height.toFloat(),paintToUse)
            canvas.drawLine(0F,i*cellSizePixel,
                width.toFloat(),i*cellSizePixel,paintToUse)
        }
    }

    private fun drawText(canvas: Canvas){
        cells?.forEach{ it ->
            it?.forEach{cell->
                val c=cell.colmun
                val r=cell.row
                val value=cell.value.toString()
                val textBound= Rect()
                textPaint.getTextBounds(value,0,value.length,textBound)
                val textWidth=textPaint.measureText(value)
                val textHeight= textBound.height()
                canvas.drawText(value,(c*cellSizePixel)+cellSizePixel/2-textWidth/2,
                    r*cellSizePixel+cellSizePixel/2-textHeight/2,textPaint)

            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return when(event?.action){
            MotionEvent.ACTION_DOWN->{
                handleTouchEvent(event.x,event.y)
                true
            }
            else->false

        }
    }

    private fun handleTouchEvent(x: Float ,y: Float){
        val possibleSelectedRow=(y/cellSizePixel).toInt()
        val possibleSelectedColumn=(x/cellSizePixel).toInt()
        listener?.onTouched(possibleSelectedRow,possibleSelectedColumn)
    }

    fun updateCell(cells:List<List<Cell>>){
        this.cells=cells
        invalidate()
    }

     fun updateSelectedCellUI(row:Int,col:Int){
        selectedRow=row
        selectedColumn=col
        invalidate()
    }

    fun registerListener(listener: SudukoBoardView.OnTouchListener){
        this.listener= this.listener
    }

    interface OnTouchListener{
        fun onTouched(row:Int,col:Int)
    }

}