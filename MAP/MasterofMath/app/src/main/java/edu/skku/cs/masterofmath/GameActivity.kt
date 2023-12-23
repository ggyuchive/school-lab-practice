package edu.skku.cs.masterofmath

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.TextView

class GameActivity : AppCompatActivity() {
    private lateinit var countDownTimer: CountDownTimer
    companion object {
        const val EXT_SCORE = "extra_key_score"
        const val EXT_HISTORY = "extra_key_history"
        var isIntegral = false
        var isDerive = false
        var x = 1
        var TargetItem = arrayListOf(1, 1, 1, 1)
        var UseNumItem = arrayListOf(1, 1, 1, 1)
        var count = 0
        var score = 0
        var history: ArrayList<Pair<String, Int>> = arrayListOf()
        var storehistory: ArrayList<Pair<String, Int>> = arrayListOf()
    }
    fun execute(score: Int, history: ArrayList<Pair<String, Int>>) {
        val intent = Intent(this, ResultActivity::class.java).apply{
            putExtra(EXT_SCORE, score)
            putExtra(EXT_HISTORY, history)
            storehistory = history
        }
        startActivity(intent)
    }

    override fun onBackPressed() {
        countDownTimer.cancel()
        isIntegral = false
        isDerive = false
        count = 0
        score = 0
        finish()
    }
    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        history.clear()
        val name = intent.getStringExtra(MainActivity.EXT_NAME)
        val time = intent.getIntExtra(MainActivity.EXT_TIME, -1)
        val mode = intent.getStringExtra(MainActivity.EXT_MODE)


        val timeTextView = findViewById<TextView>(R.id.timeTextView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val nametextview = findViewById<TextView>(R.id.nameTextView)
        val equationtview = findViewById<TextView>(R.id.equationtextView)
        val equationtextview = findViewById<TextView>(R.id.currentEquationTextView)
        val scoretextview = findViewById<TextView>(R.id.scoreTextView)

        // define 3 gridviews
        val targetgridview = findViewById<GridView>(R.id.targetNumGridView)
        val usenumgridview = findViewById<GridView>(R.id.canuseNumGridView)
        val operationgridview = findViewById<GridView>(R.id.operationGridView)


        // reset values
        nametextview.text = "Name : "+name

        equationtextview.text = ""
        TargetItem = ResetTargetNum()
        x = ResetX()
        UseNumItem = ResetUseNum()
        var Operation = arrayListOf(
            resources.getDrawable(R.drawable.op1),resources.getDrawable(R.drawable.op2),resources.getDrawable(R.drawable.op3),resources.getDrawable(R.drawable.op4)
        ,resources.getDrawable(R.drawable.op5),resources.getDrawable(R.drawable.op6),resources.getDrawable(R.drawable.op7),resources.getDrawable(R.drawable.op8)
        )
        val t = ChooseIntegral()
        var l = 0; var r = 0
        if (mode == "easy") {
            equationtview.text = "Equation"
        }
        if (mode == "hard") {
            equationtview.text = "Equation (x="+x.toString()+")"
            Operation.add(resources.getDrawable(R.drawable.op9)); Operation.add(resources.getDrawable(R.drawable.op10))
            Operation.add(resources.getDrawable(R.drawable.op11));

            if (t == 1) {Operation.add(resources.getDrawable(R.drawable.op12_1)); r = 3}
            if (t == 2) {Operation.add(resources.getDrawable(R.drawable.op12_2)); r = 4}
            if (t == 3) {Operation.add(resources.getDrawable(R.drawable.op12_3)); r = 6}
        }

        targetgridview.numColumns = 4
        usenumgridview.numColumns = 4
        operationgridview.numColumns = 4

        usenumgridview.adapter = UseNumGridViewAdapter(this, 4, UseNumItem, equationtextview)
        operationgridview.adapter = OperationGridViewAdapter(this, Operation.size, Operation, equationtextview, l, r, usenumgridview, equationtextview)
        targetgridview.adapter = TargetGridViewAdapter(this, 4, TargetItem, equationtextview, l, r)

        // CountDownTimer를 사용하여 60, 120초 동안의 시간을 측정
        countDownTimer = object : CountDownTimer((time*1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = (millisUntilFinished.toFloat() / (time*1000) * 100).toInt()
                progressBar.progress = progress
                val seconds = (millisUntilFinished / 1000).toInt()
                timeTextView.text = seconds.toString()
                targetgridview.adapter = TargetGridViewAdapter(this@GameActivity, 4, GameActivity.TargetItem, equationtextview, l, r)
                scoretextview.text = "Score : " + GameActivity.score.toString()
            }
            override fun onFinish() {
                progressBar.progress = 0
                timeTextView.text = "0"
                isIntegral = false
                isDerive = false
                count = 0
                finish()
                println(history)
                println("\n\n\n\n")
                execute(score, history)
                score = 0
            }
        }.start()
    }
}