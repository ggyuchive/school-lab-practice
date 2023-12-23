package edu.skku.cs.masterofmath

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

// extend BaseAdapter, have to write 4 function in BaseAdapter
// getCount, getItem, getItemId, getView

class ResultAdapter(val data: ArrayList<Pair<String, Int>>, val context: Context) : BaseAdapter() {
    override fun getCount(): Int {
        return data.size
    }
    // p0 th item
    override fun getItem(p0: Int): Any {
        return data[p0]
    }
    // get id=p0 item
    override fun getItemId(p0: Int): Long {
        return 0
    }
    // p0 th item's view
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val layoutInflater: LayoutInflater =
            context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
            ) as LayoutInflater

        val view = layoutInflater.inflate(R.layout.result_item, null)
        val equationView = view.findViewById<TextView>(R.id.equationtextview)
        val scoreView = view.findViewById<TextView>(R.id.scoreview)

        equationView.text = data[p0].first
        scoreView.text = "+" + data[p0].second.toString()

        return view
    }
}