package com.example.socialnetwork.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.socialnetwork.databinding.ActivityMainBinding
import com.example.socialnetwork.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        var activity: MainActivity? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MainActivity.activity = this

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding.lifecycleOwner = this
        (viewModel as MainActivityViewModel).initiateViewModel(this)
    }


}
