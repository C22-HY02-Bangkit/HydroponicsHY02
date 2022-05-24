package com.capstone.sub1.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.capstone.hidroponichy02.R
import com.capstone.hidroponichy02.utils.Utils.isValidEmail

class MyEditText : AppCompatEditText, View.OnTouchListener {
    private lateinit var clear: Drawable
    private lateinit var nameLogo: Drawable
    private lateinit var emailLogo: Drawable
    private lateinit var passwordLogo: Drawable
    private var inputEmail: Boolean = false
    private var inputsPassword: Boolean = false
    private var inputsName: Boolean = false

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, style: AttributeSet) : super(context, style) {
        init(style)
    }

    constructor(context: Context, style: AttributeSet, defStyleAttr: Int) : super(
        context,
        style,
        defStyleAttr
    ) {
        init(style, defStyleAttr)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int = 0) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MyEditText, defStyleAttr, 0)

        inputEmail = a.getBoolean(R.styleable.MyEditText_email, false)
        inputsPassword = a.getBoolean(R.styleable.MyEditText_password, false)
        inputsName = a.getBoolean(R.styleable.MyEditText_name, false)
        clear = ContextCompat.getDrawable(context, R.drawable.ic_close) as Drawable
        emailLogo = ContextCompat.getDrawable(context, R.drawable.logo_email) as Drawable
        passwordLogo = ContextCompat.getDrawable(context, R.drawable.ic_key) as Drawable
        nameLogo = ContextCompat.getDrawable(context, R.drawable.ic_person) as Drawable

        if (inputEmail) {
            setButtonDrawables(startOfTheText = emailLogo)
        } else if (inputsPassword) {
            setButtonDrawables(startOfTheText = passwordLogo)
        } else if (inputsName) {
            setButtonDrawables(startOfTheText = nameLogo)
        }

        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val input = s.toString()
                val emailError = resources.getString(R.string.invalid_email)
                val passwordError = resources.getString(R.string.invalid_password)
                val nameError = resources.getString(R.string.invalid_name)

                if (input.isNotEmpty()) {
                    showClearButton()
                    error =
                        if (inputsPassword && input.length < 6 && input.isNotEmpty()) {
                            passwordError
                        } else if (inputEmail && !input.isValidEmail()) {
                            emailError
                        }else if (inputsName && input.length > 10 && input.isNotEmpty()) {
                            nameError
                        }else{
                            null
                        }
                } else {
                    hideClearButton()
                }

            }

            override fun afterTextChanged(s: Editable) {
                val input = s.toString()
                val emailError = resources.getString(R.string.fill_email)
                val passwordError = resources.getString(R.string.fill_password)
                val nameError = resources.getString(R.string.fill_name)
                if (input.isEmpty()) {
                    hideClearButton()
                    error =
                        if (inputsPassword && input.isEmpty()) {
                            passwordError
                        } else if (inputEmail && input.isEmpty()) {
                            emailError
                        }else if (inputsName && input.isEmpty()) {
                            nameError
                        }else{
                            null
                        }
                } else {
                    showClearButton()
                }
            }
        })

        a.recycle()
    }
    private fun setButtonDrawables(
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

    private fun showClearButton() {
        when {
            inputEmail -> setButtonDrawables(
                startOfTheText = emailLogo,
                endOfTheText = clear
            )
            inputsPassword -> setButtonDrawables(
                startOfTheText = passwordLogo,
                endOfTheText = clear
            )
            inputsName -> setButtonDrawables(
                startOfTheText = nameLogo,
                endOfTheText = clear
            )
            else -> setButtonDrawables(endOfTheText = clear)
        }
    }

    private fun hideClearButton() {
        when {
            inputEmail -> setButtonDrawables(startOfTheText = emailLogo)
            inputsPassword -> setButtonDrawables(startOfTheText = passwordLogo)
            inputsName -> setButtonDrawables(startOfTheText = nameLogo)
            else -> setButtonDrawables()
        }
    }



    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val clearButtonStart: Float
            val clearButtonEnd: Float
            var isClearButtonClicked = false
            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                clearButtonEnd = (clear.intrinsicWidth + paddingStart).toFloat()
                when {
                    event.x < clearButtonEnd -> isClearButtonClicked = true
                }
            } else {
                clearButtonStart = (width - paddingEnd - clear.intrinsicWidth).toFloat()
                when {
                    event.x > clearButtonStart -> isClearButtonClicked = true
                }
            }
            if (isClearButtonClicked) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        clear =
                            ContextCompat.getDrawable(context, R.drawable.ic_close) as Drawable
                        showClearButton()
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        clear =
                            ContextCompat.getDrawable(context, R.drawable.ic_close) as Drawable
                        when {
                            text != null -> text?.clear()
                        }
                        hideClearButton()
                        return true
                    }
                    else -> return false
                }
            } else return false
        }
        return false
    }
}

