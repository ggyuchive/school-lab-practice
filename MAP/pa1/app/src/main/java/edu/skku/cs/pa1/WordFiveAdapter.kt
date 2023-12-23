package edu.skku.cs.pa1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat

class WordFiveAdapter(val data: ArrayList<WordFive>, val context: Context): BaseAdapter() {
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
        val generatedView = inflater.inflate(R.layout.word_five, null)

        val cardGroup = ArrayList<TextView>()
        cardGroup.add(generatedView.findViewById<TextView>(R.id.textView1))
        cardGroup.add(generatedView.findViewById<TextView>(R.id.textView2))
        cardGroup.add(generatedView.findViewById<TextView>(R.id.textView3))
        cardGroup.add(generatedView.findViewById<TextView>(R.id.textView4))
        cardGroup.add(generatedView.findViewById<TextView>(R.id.textView5))


        for (i: Int in 0..4) {
            // strike: 2, ball: 1, out: 0
            var tag = 0
            cardGroup[i].text = data[p0].inputString.substring(i, i+1)
            for (j: Int in 0..4) {
                if (cardGroup[i].text == data[p0].correctString.substring(j,j+1)) {
                    if (i == j) tag = 2
                    else if (tag == 0) tag = 1
                }
            }
            if (tag == 2) {
                cardGroup[i].setBackgroundColor(ContextCompat.getColor(cardGroup[i].context, R.color.background_strike))
                cardGroup[i].setTextColor(ContextCompat.getColor(cardGroup[i].context, R.color.text_strike))
            } else if (tag == 1) {
                cardGroup[i].setBackgroundColor(ContextCompat.getColor(cardGroup[i].context, R.color.background_ball))
                cardGroup[i].setTextColor(ContextCompat.getColor(cardGroup[i].context, R.color.text_ball))
            } else {
                cardGroup[i].setBackgroundColor(ContextCompat.getColor(cardGroup[i].context, R.color.background_out))
                cardGroup[i].setTextColor(ContextCompat.getColor(cardGroup[i].context, R.color.text_out))
            }
        }
        return generatedView
    }
}