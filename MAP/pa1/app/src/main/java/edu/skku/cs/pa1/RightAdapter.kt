package edu.skku.cs.pa1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat

class RightAdapter(val data: ArrayList<WordCard>, val context: Context): BaseAdapter() {
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(p0: Int): Any {
        return data[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val generatedView = inflater.inflate(R.layout.word_card, null)

        val card = generatedView.findViewById<TextView>(R.id.textView1)
        card.text = data[p0].letter
        card.setBackgroundColor(ContextCompat.getColor(card.context, R.color.background_strike))
        card.setTextColor(ContextCompat.getColor(card.context, R.color.text_strike))
        return generatedView
    }
}