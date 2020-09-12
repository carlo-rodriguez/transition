package com.example.transition

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat

class TwoActivity : AppCompatActivity() {

    private var makeTransition: Boolean = false
    private var oldIdentifier: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)
        val imageView = findViewById<ImageView>(R.id.two_image_view)
        oldIdentifier = TransitionHelper.getId(intent)
        imageView.setOnClickListener {
            change(imageView)
        }
    }

    override fun onBackPressed() {
        if (makeTransition){
            val imageView = findViewById<ImageView>(R.id.two_image_view)
            val intent = Intent(this, OneActivity::class.java)
            intent.putExtra(TransitionHelper.ID_NAME, oldIdentifier)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, imageView, "main_image_view"
            )
            startActivity(intent, options.toBundle())
            finish()
        }
        else
            supportFinishAfterTransition()
    }

    private fun change(imageView: ImageView){
        val intent = Intent(this, ThreeActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this, imageView, "three_image_view"
        )
        startActivity(intent, options.toBundle())
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        makeTransition = true
    }
}