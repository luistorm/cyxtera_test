package com.example.cyxteratest.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.cyxteratest.R
import com.example.cyxteratest.data.models.UserState
import com.example.cyxteratest.presentation.viewmodels.MainViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
    }

    private fun setListeners() {
        loginButton.setOnClickListener {
            validateUserInfo()
        }
        compositeDisposable.add(
            mainViewModel.searchUserSubject
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe({userState ->
                    userState?.let {
                        when(userState) {
                            is UserState.notFoundUser -> showNotFoundUserDialog()
                            is UserState.insertedUser -> showUserSavedMessage()
                        }
                    }
                }, Throwable::printStackTrace)
        )
    }

    private fun validateUserInfo() {
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText.text).matches()) {
            val regex = "(?=^.{8,}\$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&amp;*()_+}{&quot;:;'?/&gt;.&lt;,])(?!.*\\s).*\$".toRegex()
            if(regex.matches(passwordEditText.text.toString())) {
                mainViewModel.searchUser(emailEditText.text.toString(), passwordEditText.text.toString(), this)
            } else {
                passwordEditText.error = getString(R.string.error_password)
            }
        } else {
            emailEditText.error = getString(R.string.error_email)
        }
    }

    private fun showUserSavedMessage() {
        Toast.makeText(this, getString(R.string.description_inserted_user), Toast.LENGTH_LONG).show()
    }

    private fun showNotFoundUserDialog() {
        val builder: AlertDialog.Builder =  AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.title_not_found_user))
            .setMessage(getString(R.string.description_not_found_user))
        builder.setPositiveButton(getString(R.string.button_yes)) { dialog, _ ->
            mainViewModel.insertUser(emailEditText.text.toString(), passwordEditText.text.toString())
            dialog.dismiss()
        }
        builder.setNegativeButton(getString(R.string.button_no)) { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}
