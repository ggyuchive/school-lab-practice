package edu.skku.cs.pa2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class MazeSelectionActivity : AppCompatActivity() {
    companion object {
        const val EXT_NAME = "extra_key_name"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maze_selection)

        val name = getIntent().getStringExtra(MainActivity.EXT_NAME)
        val userNameText = findViewById<TextView>(R.id.usertextView)
        userNameText.text = name

        val client = OkHttpClient()
        val req = Request.Builder().url("http://swui.skku.edu:1399/maps").build()

        client.newCall(req).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                response.use{
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val str = response.body!!.string()
                    val typeToken = object : TypeToken<List<MazeList>>() {}.type
                    val mazelist = Gson().fromJson<List<MazeList>>(str, typeToken)

                    CoroutineScope(Dispatchers.Main).launch {
                        val listAdapter = ListViewAdapter(this@MazeSelectionActivity, mazelist)
                        val listView = findViewById<ListView>(R.id.map_select_listview)
                        listView.adapter = listAdapter
                    }
                }
            }

        })
    }
}