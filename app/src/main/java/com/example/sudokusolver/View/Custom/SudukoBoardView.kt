package layout
import android.app.Notification
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
//import com.patrickfeltes.sudokuyoutube.game.Cell
import kotlin.math.min
class SudukoBoardView(context: Context,attributeSet: AttributeSet) :View(context,attributeSet)
{
    private var sqrtSize=3
    private var size=9
    private var cellSizePixel =0F
    private var selectedRow=-1
    private var selectedColumn=-1

    private  var listner: SudukoBoardView.OnTouchListener?=null

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
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixels =Math.min(widthMeasureSpec,heightMeasureSpec)
        setMeasuredDimension(sizePixels,sizePixels)
    }

    override fun onDraw(canvas : Canvas){
        cellSizePixel=(width/size).toFloat()
        fillCells(canvas)
        drawLines(canvas)
    }

    private fun fillCells(canvas: Canvas){
        if(selectedRow==-1||selectedColumn==-1)return
        for (r in 0..size){
            for (c in 0 until  size){
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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return when(event?.action){
            MotionEvent.ACTION_DOWN->{
                handleTouchEvent(event.x,event.y)
                true
            }
            else->false

        }
    }

    private  fun handleTouchEvent(x: Float ,y: Float){
        val possibleSelectedRow=(y/cellSizePixel).toInt()
        val possibleSelectedColumn=(x/cellSizePixel).toInt()
        listner?.onTouched(possibleSelectedRow,possibleSelectedColumn)
    }

     fun updateSelectedCellUI(row:Int,col:Int){
        selectedRow=row
        selectedColumn=col
    }

    fun registerListener(listener: SudukoBoardView.OnTouchListener){
        this.listner=listner
    }

    interface OnTouchListener{
        fun onTouched(row:Int,col:Int)
    }

}