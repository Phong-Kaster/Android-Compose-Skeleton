package com.example.skeleton.core

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import com.example.skeleton.util.LanguageUtil
import com.example.skeleton.util.NetworkUtil
import com.example.skeleton.util.SystemBarUtil

open class CoreActivity() : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        // Set locale
        var context = newBase
        if (newBase != null) context = LanguageUtil(newBase).setLanguage()
        super.attachBaseContext(context)
    }

    @Composable
    open fun ComposeView() { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        SystemBarUtil.hideNavigationBar(window = this.window)
        setContent { ComposeView() }
    }

     fun isInternetConnected(): Boolean {
        return NetworkUtil.isInternetConnected(context = this)
    }
}