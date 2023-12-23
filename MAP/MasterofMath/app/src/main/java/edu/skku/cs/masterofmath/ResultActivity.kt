package edu.skku.cs.masterofmath

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val score = intent.getIntExtra(GameActivity.EXT_SCORE, -1)
        val history = GameActivity.storehistory

        val scoretxt = findViewById<TextView>(R.id.scoretextview)
        scoretxt.text = "Score : $score"

        val restart = findViewById<Button>(R.id.restartbutton)
        restart.setOnClickListener {
            finish()
        }

        print(history)
        println("\n\n\n\n\n")

        val myAdapter = ResultAdapter(history, applicationContext)
        val listView = findViewById<ListView>(R.id.listview)
        listView.adapter = myAdapter
    }
}