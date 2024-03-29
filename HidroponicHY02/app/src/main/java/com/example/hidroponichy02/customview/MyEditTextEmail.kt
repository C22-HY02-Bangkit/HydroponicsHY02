package com.example.hidroponichy02.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.example.hidroponichy02.R
import com.google.android.material.textfield.TextInputEditText

class MyEditTextEmail : TextInputEditText, View.OnTouchListener {

    private lateinit var clearButton: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttrs: Int) : super(
        context,
        attrs,
        defStyleAttrs
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setBackgroundResource(R.drawable.bg_border)
        textSize = 15f
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        clearButton = ContextCompat.getDrawable(context, R.drawable.ic_close) as Drawable

        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) deleteButtonShow() else deleteButtonHide()
            }

            override fun afterTextChanged(s: Editable) {
                if (s.toString().isEmpty()) errorMessege()
                else if (!isEmailValid(s)) errorValidationEmail()
            }

        })
    }

    private fun isEmailValid(email: CharSequence): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun errorMessege() {
        error = resources.getString(R.string.error_messege_email)
    }

    private fun errorValidationEmail() {
        error = resources.getString(R.string.invalid_email)
    }

    private fun deleteButtonShow() {
        setButton(endOfTheText = clearButton)
    }

    private fun deleteButtonHide() {
        setButton()
    }

    private fun setButton(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val clearButtonStart: Float
            val clearButtonEnd: Float
            var isClearButtonClicked = false

            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                clearButtonEnd = (clearButton.intrinsicWidth + paddingStart).toFloat()
                if (event.x < clearButtonEnd) isClearButtonClicked = true
            } else {
                clearButtonStart = (width - paddingEnd - clearButton.intrinsicWidth).toFloat()
                if (event.x > clearButtonStart) isClearButtonClicked = true
            }

            if (isClearButtonClicked) {
                return when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        clearButton =
                            ContextCompat.getDrawable(context, R.drawable.ic_close) as Drawable
                        deleteButtonShow()
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        clearButton =
                            ContextCompat.getDrawable(context, R.drawable.ic_close) as Drawable
                        if (text != null) text?.clear()
                        deleteButtonHide()
                        true
                    }
                    else -> false
                }
            } else return false
        }
        return false
    }

}