package com.visa.timesreader

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.visa.timesreader.databinding.ActivityNewsDetailBinding

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var activityBinding: ActivityNewsDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setSupportActionBar(activityBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val url = intent.getStringExtra("url")
        if (url != null) {
            activityBinding.webView.loadUrl(url)
        } else {
            Toast.makeText(this, "Error loading URL", Toast.LENGTH_SHORT).show()
        }

        val view = activityBinding.root
        setContentView(view)
    }
}