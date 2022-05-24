package com.capstone.hidroponichy02.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.capstone.hidroponichy02.R
import com.capstone.hidroponichy02.databinding.ActivityLoginBinding
import com.capstone.hidroponichy02.utils.Helper
import com.capstone.hidroponichy02.utils.Utils.isValidEmail
import com.capstone.hidroponichy02.viewmodel.LoginViewModel
import com.capstone.hidroponichy02.viewmodel.ViewModelFactory
import com.example.storyapp.model.UserModel
import com.example.storyapp.model.UserPreference
import com.example.storyapp.response.ResultResponse
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val loginViewModel: LoginViewModel by viewModels{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setEnableBtnLogin()
        LoginOnClick()
        registerOnClick()
    }
    private fun editTextListener() {
        binding.emailLog.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
        binding.passwordLog.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

    }
    private fun LoginOnClick() {
        binding.btnLog.setOnClickListener {
            val email = binding.emailLog.text.toString()
            val pass = binding.passwordLog.text.toString()

            loginViewModel.signIn(email, pass).observe(this) {
                when (it) {
                    is ResultResponse.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ResultResponse.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val user = UserModel(
                            it.data.fullname,
                            email,
                            pass,
                            it.data.userId,
                            it.data.token,
                            true
                        )
                        showAlertDialog(true, getString(R.string.sign_in_success))

                        val userPref = UserPreference.getInstance(dataStore)
                        lifecycleScope.launchWhenStarted {
                            userPref.saveUser(user)
                        }
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
                setMessage(getString(R.string.sign_in_success))
                setPositiveButton(getString(R.string.continu)) { _, _ ->
                    val intent = Intent(context, MainActivity::class.java)
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
                setMessage(getString(R.string.sign_in_failed) + ", $message")
                setPositiveButton(getString(R.string.continu)) { _, _ ->
                    binding.progressBar.visibility = View.GONE
                }
                create()
                show()

            }
        }
    }

    private fun setMyButtonEnable() {
        val resultPass = binding.passwordLog.text
        val resultEmail = binding.emailLog.text

        binding.btnLog.isEnabled = resultPass != null && resultEmail != null &&
                binding.passwordLog.text.toString().length >= 6 &&
                Helper.isEmailValid(binding.emailLog.text.toString())
    }    private fun setEnableBtnLogin() {
        binding.passwordLog.addTextChangedListener {
            val password = binding.passwordLog.text
            val email = binding.emailLog.text
            val buttonShouldEnabled = (password.toString()
                .isNotEmpty() && password.toString().length >= 6) && (email.toString()
                .isNotEmpty() && email.toString().isValidEmail())
            binding.btnLog.isEnabled = buttonShouldEnabled
        }
    }
    private fun registerOnClick() {
        binding.btnReg.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}