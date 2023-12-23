package edu.skku.cs.pa2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView

class ListViewAdapter(val context : Context, val items:List<MazeList>): BaseAdapter() {

    override fun getCount(): Int {
        return items.size
    }
    override fun getItem(p0: Int): Any {
        return items.get(p0)
    }
    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.maze_entry, null)

        val mazename = view.findViewById<TextView>(R.id.mazetextView)
        val size = view.findViewById<TextView>(R.id.sizetextView)
        val btn = view.findViewById<Button>(R.id.startbutton)

        mazename.text = items.get(p0).name.toString()
        size.text = items.get(p0).size.toString()

        btn.setOnClickListener {
            val intent = Intent(context, MazeActivity::class.java)
                .apply{
                    putExtra(MazeSelectionActivity.EXT_NAME, mazename.text)
                }
            context.startActivity(intent)
        }

        return view
    }
}