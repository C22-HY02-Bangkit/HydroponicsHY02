package com.capstone.hidroponichy02.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.capstone.hidroponichy02.R
import com.capstone.hidroponichy02.databinding.ActivityRegisterBinding
import com.capstone.hidroponichy02.utils.Utils.isValidEmail
import com.capstone.hidroponichy02.viewmodel.RegisterViewModel
import com.example.storyapp.response.ResultResponse


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setButtonShouldEnabled()
        setSubmitButton()
        setBackToLogin()
    }

    private fun setBackToLogin() {
        binding.back.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun setButtonShouldEnabled() {
        binding.apply {
            nameReg.addTextChangedListener {
                setButtonEnabled()
            }
            emailReg.addTextChangedListener {
                setButtonEnabled()
            }
            passwordReg.addTextChangedListener {
                setButtonEnabled()
            }
        }
    }

    private fun setButtonEnabled() {
        val username = binding.nameReg.text.toString()
        val password = binding.passwordReg.text.toString()
        val email = binding.emailReg.text.toString()
        val buttonShouldEnabled = (password
            .isNotEmpty() && password.length >= 6) && (email
            .isNotEmpty() && email.isValidEmail() && username.isNotEmpty())
        binding.btnRegg.isEnabled = buttonShouldEnabled
    }

    private fun setSubmitButton() {
        binding.btnRegg.setOnClickListener {
            val fullname = binding.nameReg.text.toString()
            val email = binding.emailReg.text.toString()
            val password = binding.passwordReg.text.toString()

            registerViewModel.signUp(fullname, email, password).observe(this) {
                when (it) {
                    is ResultResponse.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ResultResponse.Success -> {
                        binding.progressBar.visibility = View.GONE
                        showAlertDialog(true, getString(R.string.sign_up_success))
                    }
                    is ResultResponse.Error -> {
                        binding.progressBar.visibility = View.GONE
                        showAlertDialog(false, it.error)
                    }
                }
            }
        }
    }
    private fun showAlertDialog(param: Boolean, message: String) {
        if (param) {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.information))
                setMessage(getString(R.string.sign_up_success))
                setPositiveButton(getString(R.string.continu)) { _, _ ->
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
                create()
                show()
            }
        } else {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.information))
                setMessage(getString(R.string.sign_up_failed) + ", $message")
                setPositiveButton(getString(R.string.continu)) { _, _ ->
                    binding.progressBar.visibility = View.GONE
                }
                create()
                show()
            }
        }
    }

}