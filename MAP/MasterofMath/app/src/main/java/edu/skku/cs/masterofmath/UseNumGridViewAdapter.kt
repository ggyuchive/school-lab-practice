package edu.skku.cs.masterofmath

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class UseNumGridViewAdapter(val context : Context, val size: Int, val items: ArrayList<Int>, val eq: TextView): BaseAdapter() {

    override fun getCount(): Int {
        return size
    }
    override fun getItem(p0: Int): Any {
        return items.get(p0)
    }
    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.number_card, null)
        val button = view.findViewById<Button>(R.id.button)
        button.text = items.get(p0).toString()

        button.setOnClickListener {
            val toadd = eq.text.toString() + items.get(p0).toString()
            if (GameActivity.count == 10) {
                Toast.makeText(context, "10회 버튼을 모두 사용하셨습니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                if (GameActivity.isIntegral) {
                    eq.text = eq.text.toString().substring(0..eq.text.length - 4) + items.get(p0)
                        .toString() + ")dx"
                } else if (GameActivity.isDerive) {
                    eq.text = eq.text.toString().substring(0..eq.text.length - 2) + items.get(p0)
                        .toString() + ")"
                } else {
                    eq.text = toadd
                }
                GameActivity.count++
            }
        }

        return view
    }
}