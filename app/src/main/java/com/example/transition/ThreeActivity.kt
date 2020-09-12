package com.example.transition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ThreeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_three)
    }

    override fun onBackPressed() {
        val data = Intent()
        setResult(RESULT_OK, data)
        supportFinishAfterTransition()
    }
}