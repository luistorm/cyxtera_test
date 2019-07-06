package com.example.cyxteratest.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cyxteratest.R
import com.example.cyxteratest.presentation.viewmodels.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
