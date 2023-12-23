package edu.skku.cs.pa2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class MazeActivity : AppCompatActivity() {
    var size = 0
    var maze = ArrayList<Int>()
    var arr = ArrayList<ArrayList<Int>>()
    var curx = 0
    var cury = 0
    var hintx = -1
    var hinty = -1
    var curdir = 0
    var ishint = false
    var hint = true
    var turn = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maze)
        val turntxt = findViewById<TextView>(R.id.turncounttextView)
        val hbtn = findViewById<Button>(R.id.hintbutton)
        val ubtn = findViewById<Button>(R.id.upbutton)
        val dbtn = findViewById<Button>(R.id.downbutton)
        val lbtn = findViewById<Button>(R.id.leftbutton)
        val rbtn = findViewById<Button>(R.id.rightbutton)
        val gridView = findViewById<GridView>(R.id.mazegridView)
        val dx = listOf(0,1,0,-1)
        val dy = listOf(1,0,-1,0)

        fun dist (sx:Int, sy:Int, ex:Int, ey:Int):Int {
            var visited = Array(size) {BooleanArray(size)}
            var dst = Array(size) {IntArray(size)}
            var q = LinkedList<Pair<Int,Int>>()
            q.offer(Pair(sx,sy))
            visited[sx][sy]=true
            dst[sx][sy]=0
            while(!q.isEmpty()) {
                val (x,y) = q.poll()
                for (i in 0..3) {
                    if (((arr[x][y] shr i) and 1) == 0) {
                        val nx = x+dx[i]
                        val ny = y+dy[i]
                        if (!visited[nx][ny]) {
                            visited[nx][ny]=true
                            dst[nx][ny]=dst[x][y]+1
                            q.offer(Pair(nx,ny))
                        }
                    }
                }
            }

            return dst[ex][ey]
        }
        hbtn.setOnClickListener {
            // r,d,l,u
            var distance = mutableListOf(1000000,1000000,1000000,1000000)
            if (hint) {
                for (i in 0..3) {
                    if (((arr[curx][cury] shr i) and 1) == 0) {
                        distance[i] = dist(curx+dx[i], cury+dy[i], size-1, size-1)
                    }
                }
                var minind = 0
                var minval = 10000000
                for (i in 0..3) {
                    if (minval > distance[i]) {
                        minval = distance[i]
                        minind = i
                    }
                }
                //print(distance)
                //print("\n\n\n\n\n")
                hintx=curx+dx[minind]
                hinty=cury+dy[minind]
                ishint = true
                gridView.adapter = GridViewAdapter(this@MazeActivity, size*size, maze, curx, cury, curdir, ishint, hintx, hinty)
            }
            hint = false
        }
        ubtn.setOnClickListener {
            curdir = 0
            if (((arr[curx][cury] shr 3) and 1) == 0) {
                turn++
                turntxt.text = "Turn : "+turn.toString()
                if (curx-1==hintx && cury==hinty) ishint = false
                gridView.adapter = GridViewAdapter(this@MazeActivity, size*size, maze, --curx, cury, 0, ishint, hintx, hinty)
            }
            else {
                gridView.adapter = GridViewAdapter(this@MazeActivity, size*size, maze, curx, cury, 0, ishint, hintx, hinty)
            }
        }
        rbtn.setOnClickListener {
            curdir = 1
            if (((arr[curx][cury] shr 0) and 1) == 0) {
                turn++
                turntxt.text = "Turn : "+turn.toString()
                if (curx==hintx && cury+1==hinty) ishint = false
                gridView.adapter = GridViewAdapter(this@MazeActivity, size*size, maze, curx, ++cury, 1, ishint, hintx, hinty)
            }
            else {
                gridView.adapter = GridViewAdapter(this@MazeActivity, size*size, maze, curx, cury, 1, ishint, hintx, hinty)
            }
        }
        dbtn.setOnClickListener {
            curdir = 2
            if (((arr[curx][cury] shr 1) and 1) == 0) {
                turn++
                turntxt.text = "Turn : "+turn.toString()
                if (curx+1==hintx && cury==hinty) ishint = false
                gridView.adapter = GridViewAdapter(this@MazeActivity, size*size, maze, ++curx, cury, 2, ishint, hintx, hinty)
            }
            else {
                gridView.adapter = GridViewAdapter(this@MazeActivity, size*size, maze, curx, cury, 2, ishint, hintx, hinty)
            }
        }
        lbtn.setOnClickListener {
            curdir = 3
            if (((arr[curx][cury] shr 2) and 1) == 0) {
                turn++
                turntxt.text = "Turn : "+turn.toString()
                if (curx==hintx && cury-1==hinty) ishint = false
                gridView.adapter = GridViewAdapter(this@MazeActivity, size*size, maze, curx, --cury, 3, ishint, hintx, hinty)
            }
            else {
                gridView.adapter = GridViewAdapter(this@MazeActivity, size*size, maze, curx, cury, 3, ishint, hintx, hinty)
            }
        }

        val name = getIntent().getStringExtra(MazeSelectionActivity.EXT_NAME)
        val url = "http://swui.skku.edu:1399/maze/map?name="+name
        val client = OkHttpClient()
        val req = Request.Builder().url(url).build()

        client.newCall(req).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                response.use{
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val str = response.body!!.string()
                    val data = Gson().fromJson(str, MazeInfo::class.java)

                    val mazein = data.maze
                    val rows = mazein?.split('\n')?.filter{it.isNotBlank()}
                    size = rows?.size!!-1

                    for (i in 1 ..rows?.size!!-1) {
                        val sp = rows[i].split(' ').filter{it.isNotBlank()}.map{it.toInt()}
                        val toput = ArrayList<Int>()
                        for (j in 0 .. sp.size-1) {
                            maze.add(sp[j])
                            toput.add(sp[j])
                        }
                        arr.add(toput)
                    }
                    //print(size)
                    //print(arr)
                    //print("\n\n\n\n\n")
                    CoroutineScope(Dispatchers.Main).launch {
                        gridView.numColumns = size
                        gridView.adapter = GridViewAdapter(this@MazeActivity, size*size, maze, curx, cury, 0, false, hintx, hinty)
                    }
                }
            }
        })
    }
}