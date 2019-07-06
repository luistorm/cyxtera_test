package com.example.cyxteratest.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cyxteratest.R
import com.example.cyxteratest.data.models.UserAttempt
import com.example.cyxteratest.presentation.adapters.AttemptsAdapter
import com.example.cyxteratest.presentation.viewmodels.AttemptsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_attempts_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class AttemptsListActivity : AppCompatActivity() {

    private val attemptsViewModel: AttemptsViewModel by viewModel()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attempts_list)
        attemptsViewModel.getAttempts(intent.extras.getString(USER_EMAIL, ""), this)
        setListeners()
    }

    private fun setListeners() {
        compositeDisposable.add(
            attemptsViewModel.getAttemptsSubject
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe({
                    loadAttemptsData(it)
                }, Throwable::printStackTrace)
        )
    }

    private fun loadAttemptsData(attempts: List<UserAttempt>) {
        attemptsRecyclerView.layoutManager = LinearLayoutManager(this)
        attemptsRecyclerView.adapter = AttemptsAdapter(attempts)
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    companion object {
        const val USER_EMAIL = "USER_EMAIL"
    }
}
