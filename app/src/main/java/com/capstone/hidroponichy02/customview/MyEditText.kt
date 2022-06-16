package com.capstone.hidroponichy02.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.capstone.hidroponichy02.R
import java.util.regex.Pattern

class MyEditText : AppCompatEditText, View.OnTouchListener {
    private lateinit var clear: Drawable
    private lateinit var emailLogo: Drawable
    private lateinit var passwordlogo: Drawable
    private var inputEmail: Boolean = false
    private var inputsPassword: Boolean = false

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

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setBackgroundResource(R.drawable.bg_border)
        textSize = 15f
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int = 0) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MyEditText, defStyleAttr, 0)

        inputEmail = a.getBoolean(R.styleable.MyEditText_email, false)
        inputsPassword = a.getBoolean(R.styleable.MyEditText_password, false)
        clear = ContextCompat.getDrawable(context, R.drawable.ic_close) as Drawable
        emailLogo = ContextCompat.getDrawable(context, R.drawable.logo_email) as Drawable
        passwordlogo = ContextCompat.getDrawable(context, R.drawable.ic_key) as Drawable

        if (inputEmail) {
            setButtonDrawables(startOfTheText = emailLogo)
        } else if (inputsPassword) {
            setButtonDrawables(startOfTheText = passwordlogo)
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

                if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
                error =
                    if (inputsPassword && input.length < 6 && input.isNotEmpty()) {
                        passwordError
                    } else if (inputEmail && !input.isValidEmail()) {
                        emailError
                    } else {
                        null
                    }
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
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
                startOfTheText = passwordlogo,
                endOfTheText = clear
            )
            else -> setButtonDrawables(endOfTheText = clear)
        }
    }

    private fun hideClearButton() {
        when {
            inputEmail -> setButtonDrawables(startOfTheText = emailLogo)
            inputsPassword -> setButtonDrawables(startOfTheText = passwordlogo)
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

fun String.isValidEmail(): Boolean {
    val pattern: Pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(this).matches()
}

fun closeKeyboard(activity: AppCompatActivity) {
    val view: View? = activity.currentFocus
    if (view != null) {
        val imm: InputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

