package com.example.viewmodeltesting.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodeltesting.R
import com.example.viewmodeltesting.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        binding.viewModel = mainActivityViewModel
        binding.lifecycleOwner = this

        binding.etEdittext.addTextChangedListener {
            mainActivityViewModel.updateTextView(it.toString())
        }

        binding.btnGetRandomUser.setOnClickListener {
            mainActivityViewModel.uploadRandomUser()
        }
    }

    override fun onResume() {
        super.onResume()
        mainActivityViewModel.getCurrentMessage()
    }

    override fun onDestroy() {
        mainActivityViewModel.saveCurrentMessage(binding.tvTextView.text.toString())
        super.onDestroy()
    }
}