package edu.skku.cs.pa1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // read file and store

        val wordList = ArrayList<String>()
        val inputStream: InputStream = assets.open("wordle_words.txt")
        val reader = BufferedReader(InputStreamReader(inputStream))

        var line: String? = reader.readLine()
        while (line != null) {
            wordList.add(line)
            line = reader.readLine()
        }

        inputStream.close()
        reader.close()

        // allocated adapter, variable
        val editText = findViewById<EditText>(R.id.editText)
        val submitButton = findViewById<Button>(R.id.buttonSubmit)

        val wordfiveitems = ArrayList<WordFive>()
        val wordfiveadapter = WordFiveAdapter(wordfiveitems, applicationContext)
        findViewById<ListView>(R.id.listViewUpper).adapter = wordfiveadapter

        val leftitems = ArrayList<WordCard>()
        val leftadapter = LeftAdapter(leftitems, applicationContext)
        findViewById<ListView>(R.id.listViewLeft).adapter = leftadapter

        val middleitems = ArrayList<WordCard>()
        val middleadapter = MiddleAdapter(middleitems, applicationContext)
        findViewById<ListView>(R.id.listViewMiddle).adapter = middleadapter

        val rightitems = ArrayList<WordCard>()
        val rightadapter = RightAdapter(rightitems, applicationContext)
        findViewById<ListView>(R.id.listViewRight).adapter = rightadapter

        // implement game!
        var randInt = Random.nextInt(0, 5756)
        val correctString = wordList[randInt].uppercase()
        // 26 sized (0: not typed, 1: out, 2: ball, 3: strike)
        var position: Array<Int> = Array(26) {0}

        submitButton.setOnClickListener {
            var inputText = editText.text.toString()

            if (inputText.length != 5) {
                Toast.makeText(this,"Word '$inputText' not in dictionary!", Toast.LENGTH_SHORT).show()
            }
            else if (!wordList.contains(inputText.lowercase())) {
                Toast.makeText(this,"Word '$inputText' not in dictionary!", Toast.LENGTH_SHORT).show()
            }
            else {
                inputText = inputText.uppercase()
                wordfiveitems.add(WordFive(inputText, correctString))
                wordfiveadapter.notifyDataSetChanged()

                for (i:Int in 0..4) {
                    val charToPut = inputText.substring(i,i+1)
                    val beforestate = position[charToPut[0].code-'A'.code]
                    var afterstate = 2
                    if (inputText[i] == correctString[i]) {
                        afterstate = 3
                    }
                    var tag = 0
                    for (j:Int in 0..4) {
                        if (inputText[i] == correctString[j]) tag=1
                    }
                    if (tag == 0) afterstate = 1

                    if (beforestate < afterstate) {
                        if (afterstate == 1) {
                            leftitems.add(WordCard(charToPut))
                        }
                        else if (afterstate == 2) {
                            if (beforestate == 1) {
                                leftitems.remove(leftitems.find {it.letter == charToPut})
                            }
                            middleitems.add(WordCard(charToPut))
                        }
                        if (afterstate == 3) {
                            if (beforestate == 1) {
                                leftitems.remove(leftitems.find {it.letter == charToPut})
                            }
                            else if (beforestate == 2) {
                                middleitems.remove(middleitems.find {it.letter == charToPut})
                            }
                            rightitems.add(WordCard(charToPut))
                        }
                        position[charToPut[0].code-'A'.code] = afterstate
                    }
                }

                // 바뀐 결과 적용
                leftadapter.notifyDataSetChanged()
                middleadapter.notifyDataSetChanged()
                rightadapter.notifyDataSetChanged()

                // 입력된 단어 비우기
                editText.text.clear()
            }
        }
    }
}