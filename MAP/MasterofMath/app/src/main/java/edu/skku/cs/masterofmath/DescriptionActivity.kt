package edu.skku.cs.masterofmath

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton

class DescriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        val beforeButton = findViewById<Button>(R.id.beforebutton)

        beforeButton.setOnClickListener {
            finish()
        }
    }
}