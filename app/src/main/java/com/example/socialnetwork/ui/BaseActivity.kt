package com.example.socialnetwork.ui

import androidx.appcompat.app.AppCompatActivity
import com.example.socialnetwork.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {
    protected var viewModel: BaseViewModel? = null

    override fun onBackPressed() {
        super.onBackPressed()
        (supportFragmentManager.findFragmentById(R.id.fragment) as BaseFragment?)?.onBackPressed()
    }
}