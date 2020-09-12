package com.example.transition

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat

class OneActivity : AppCompatActivity(), TransitionHelperDelegate {

    private lateinit var transitionHelper: TransitionHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageView = findViewById<ImageView>(R.id.main_image_view)
        imageView.setOnClickListener {
            change(imageView)
        }
        transitionHelper = TransitionHelper(intent, this)
    }

    private fun change(imageView: ImageView){
        val intent = Intent(this, TwoActivity::class.java)
        intent.putExtra(TransitionHelper.ID_NAME, transitionHelper.identifier)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this, imageView, "two_image_view"
        )
        startActivity(intent, options.toBundle())
    }

    override fun onResume() {
        super.onResume()
        transitionHelper.register(this)
        transitionHelper.clean(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        transitionHelper.unregister(this)
    }

    override fun stopActivity() {
        finish()
    }
}