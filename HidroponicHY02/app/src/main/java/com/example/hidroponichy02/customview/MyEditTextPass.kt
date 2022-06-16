package com.example.hidroponichy02.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.example.hidroponichy02.R
import com.google.android.material.textfield.TextInputEditText

class MyEditTextPass : TextInputEditText, View.OnTouchListener {

    private lateinit var visibilityIcon: Drawable

    constructor (context: Context) : super(context) {
        init()
    }

    constructor (context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor (context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setBackgroundResource(R.drawable.bg_border)
        textSize = 15f
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        visibilityIconShow()
    }

    private fun init() {
        visibilityIcon =
            ContextCompat.getDrawable(context, R.drawable.ic_visibility_off) as Drawable

        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (s.toString().length < 6) showError()
            }
        })
    }

    private fun showError() {
        error = context.getString(R.string.invalid_password)
    }

    private fun visibilityIconShow() {
        setButton(endOfTheText = visibilityIcon)
    }

    private fun visibilityIconHide() {
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
            val visibilityButtonStart: Float
            val visibilityButtonEnd: Float
            var isVisibilityButtonClicked = false

            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                visibilityButtonEnd = (visibilityIcon.intrinsicWidth + paddingStart).toFloat()
                if (event.x < visibilityButtonEnd) isVisibilityButtonClicked = true
            } else {
                visibilityButtonStart =
                    (width - paddingEnd - visibilityIcon.intrinsicWidth).toFloat()
                if (event.x > visibilityButtonStart) isVisibilityButtonClicked = true
            }

            if (isVisibilityButtonClicked) {
                return when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        visibilityIconHide()
                        if (transformationMethod.equals(HideReturnsTransformationMethod.getInstance())) {
                            transformationMethod = PasswordTransformationMethod.getInstance()
                            visibilityIcon = ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_visibility_off
                            ) as Drawable
                            visibilityIconShow()
                        } else {
                            transformationMethod = HideReturnsTransformationMethod.getInstance()
                            visibilityIcon = ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_visibility
                            ) as Drawable
                            visibilityIconShow()
                        }
                        true
                    }
                    else -> false
                }
            } else return false
        }
        return false
    }
}