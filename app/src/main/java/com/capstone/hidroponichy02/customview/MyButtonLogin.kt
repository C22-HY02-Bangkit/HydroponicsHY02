package com.capstone.hidroponichy02.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.capstone.hidroponichy02.R

class MyButtonLogin : AppCompatButton {

    private lateinit var enabledBackground: Drawable
    private lateinit var disabledBackground: Drawable
    private var txtColor: Int = 1

    constructor (context: Context) : super(context) {
        init()
    }

    constructor (context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor (context: Context, attrs: AttributeSet, defStyleAttrs: Int) : super(
        context,
        attrs,
        defStyleAttrs
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = if (isEnabled) enabledBackground else disabledBackground

        textSize = 15f
        gravity = Gravity.CENTER
        text =
            if (isEnabled) resources.getString(R.string.login) else resources.getString(R.string.fill_all)
    }

    private fun init() {
        txtColor = ContextCompat.getColor(context, R.color.black)
        enabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button) as Drawable
        disabledBackground =
            ContextCompat.getDrawable(context, R.drawable.bg_button_disable) as Drawable
    }
}