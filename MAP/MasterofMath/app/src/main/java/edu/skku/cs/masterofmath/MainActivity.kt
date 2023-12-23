package edu.skku.cs.masterofmath

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    companion object {
        const val EXT_NAME = "extra_key_name"
        const val EXT_TIME = "extra_key_time"
        const val EXT_MODE = "extra_key_mode"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val descButton = findViewById<Button>(R.id.gamediscriptionbutton)
        val gamemodegrp = findViewById<RadioGroup>(R.id.gamemodeGroup)
        val easybuttonid = R.id.easyButton
        val hardbuttonid = R.id.hardButton
        val timegrp = findViewById<RadioGroup>(R.id.timeGroup)
        val onebuttonid = R.id.oneminButton
        val twobuttonid = R.id.twominButton
        val nameText = findViewById<EditText>(R.id.editTextName)
        val startButton = findViewById<Button>(R.id.gamestartbutton)

        descButton.setOnClickListener {
            val intent = Intent(this, DescriptionActivity::class.java).apply{}
            startActivity(intent)
        }
        startButton.setOnClickListener {
            val gamemode = gamemodegrp.checkedRadioButtonId
            val time = timegrp.checkedRadioButtonId
            val name = nameText.text.toString()
            if (gamemode == -1 && time == -1) Toast.makeText(this, "Mode와 Time을 입력하세요.", Toast.LENGTH_SHORT).show()
            else if (gamemode == -1) Toast.makeText(this, "Mode를 입력하세요.", Toast.LENGTH_SHORT).show()
            else if (time == -1) Toast.makeText(this, "Time을 입력하세요.", Toast.LENGTH_SHORT).show()
            else if (name.isEmpty()) Toast.makeText(this, "이름을 입력하세요.", Toast.LENGTH_SHORT).show()
            else {
                val intent = Intent(this, GameActivity::class.java).apply{
                    if (time == onebuttonid) putExtra(EXT_TIME, 60)
                    if (time == twobuttonid) putExtra(EXT_TIME, 120)
                    if (gamemode == easybuttonid) putExtra(EXT_MODE, "easy")
                    if (gamemode == hardbuttonid) putExtra(EXT_MODE, "hard")
                    putExtra(EXT_NAME, name)
                }
                startActivity(intent)
            }
        }
    }
}