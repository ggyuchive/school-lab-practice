package edu.skku.cs.pa2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class MainActivity : AppCompatActivity() {
    companion object {
        const val EXT_NAME = "extra_key_name"
    }
    fun execute(name:String, editTextName:EditText) {
        val intent = Intent(this, MazeSelectionActivity::class.java)
            .apply{
                putExtra(EXT_NAME, name)
            }
        startActivity(intent)
        editTextName.text.clear()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.button)
        val client = OkHttpClient()
        val editTextName = findViewById<EditText>(R.id.editTextTextPersonName)


        btn.setOnClickListener {
            val name = editTextName.text.toString()
            val json = Gson().toJson(GetName(name))
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val req = Request.Builder().url("http://swui.skku.edu:1399/users").post(json.toString().toRequestBody(mediaType)).build()

            client.newCall(req).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response) {
                    response.use{
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
                        val str = response.body!!.string()
                        val data = Gson().fromJson(str, IsSuccess::class.java)
                        val succeed = data.success
                        CoroutineScope(Dispatchers.Main).launch {
                            if (succeed) {
                                execute(name, editTextName)
                            }
                            else {
                                Toast.makeText(this@MainActivity,"Wrong User Name", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            })
        }
    }
}