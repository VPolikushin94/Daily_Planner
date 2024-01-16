package com.example.simbirsoft.notes.ui.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.simbirsoft.R
import java.lang.Integer.min

class DayHourView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Shapes
    private var backgroundRectF: RectF = RectF()
    private var dividerRectF: RectF = RectF()

    // Paints
    private val backgroundPaint: Paint = Paint(ANTI_ALIAS_FLAG)
    private val dividerPaint: Paint = Paint(ANTI_ALIAS_FLAG)
    private val textValuePaint: Paint = Paint(ANTI_ALIAS_FLAG)

    // Colors
    var backgroundRectColor: Int = DEFAULT_BACKGROUND_COLOR
    var dividerRectColor: Int = DEFAULT_DIVIDER_COLOR
    var textColor: Int = DEFAULT_TEXT_COLOR

    //Sizes
    var textSize = DEFAULT_TEXT_SIZE

    //Text values
    var startHour = ""
    var finishHour = ""

    private var screenCenterHorizontal = 0
    private var screenCenterVertical = 0

    init {
        parseAttr(attrs)

        backgroundPaint.apply {
            style = Paint.Style.FILL
            color = backgroundRectColor
        }
        dividerPaint.apply {
            style = Paint.Style.FILL
            color = dividerRectColor
        }
        textValuePaint.apply {
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER
            color = textColor
            textSize = this@DayHourView.textSize
        }
    }

    private fun parseAttr(attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.DayHourView,
            0,
            0
        )

        backgroundRectColor = typedArray.getColor(R.styleable.DayHourView_backgroundColor, DEFAULT_BACKGROUND_COLOR)
        dividerRectColor = typedArray.getColor(R.styleable.DayHourView_dividerColor, DEFAULT_DIVIDER_COLOR)
        textColor = typedArray.getColor(R.styleable.DayHourView_android_textColor, DEFAULT_TEXT_COLOR)
        textSize = typedArray.getDimension(R.styleable.DayHourView_android_textSize, DEFAULT_TEXT_SIZE)
        startHour = typedArray.getString(R.styleable.DayHourView_startHour) ?: DEFAULT_TEXT
        finishHour = typedArray.getString(R.styleable.DayHourView_finishHour) ?: DEFAULT_TEXT

        typedArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        screenCenterHorizontal = w / 2
        screenCenterVertical = h / 2

        val backgroundRect = Rect().apply {
            set(
                0,
                0,
                w,
                h
            )
        }
        backgroundRectF = RectF(backgroundRect)

        val dividerRect = Rect().apply {
            set(
                screenCenterHorizontal - DIVIDER_WIDTH / 2 + paddingStart - paddingEnd,
                screenCenterVertical - DIVIDER_HEIGHT / 2 + (paddingTop - paddingBottom) / 2,
                screenCenterHorizontal + DIVIDER_WIDTH / 2 + paddingStart - paddingEnd,
                screenCenterVertical + DIVIDER_HEIGHT / 2 + (paddingTop - paddingBottom) / 2
            )
        }
        dividerRectF = RectF(dividerRect)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 94
        val desiredHeight = 120

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        var width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> min(desiredWidth, widthSize)
            else -> desiredWidth
        }
        width += paddingStart + paddingEnd

        var height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> min(desiredHeight, heightSize)
            else -> desiredHeight
        }
        height += paddingTop + paddingBottom

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(backgroundRectF, CARD_RADIUS, CARD_RADIUS, backgroundPaint)
        canvas.drawText(startHour, screenCenterHorizontal.toFloat() + paddingStart - paddingEnd, 0 + paddingTop - textValuePaint.fontMetrics.top, textValuePaint)
        canvas.drawRoundRect(dividerRectF, CARD_RADIUS, CARD_RADIUS, dividerPaint)
        canvas.drawText(finishHour, screenCenterHorizontal.toFloat() + paddingStart - paddingEnd, 0 + height - paddingBottom - textValuePaint.fontMetrics.bottom, textValuePaint)
    }

    companion object {
        private const val DEFAULT_BACKGROUND_COLOR = Color.BLUE
        private const val DEFAULT_TEXT_COLOR = Color.BLACK
        private const val DEFAULT_DIVIDER_COLOR = Color.WHITE
        private const val DEFAULT_TEXT_SIZE = 16f
        private const val DEFAULT_TEXT = "0:00"
        private const val CARD_RADIUS = 10f
        private const val DIVIDER_WIDTH = 4
        private const val DIVIDER_HEIGHT = 32
    }
}