package com.buily.filternew

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.buily.filternew.databinding.ActivityWebviewBinding

class WebViewActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityWebviewBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_webview)

        binding.webView.loadUrl(intent.getStringExtra("link"))
    }
}