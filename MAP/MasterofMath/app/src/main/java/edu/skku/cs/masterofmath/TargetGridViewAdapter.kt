package edu.skku.cs.masterofmath

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class TargetGridViewAdapter(val context : Context, val size: Int, val items: ArrayList<Int>, val eq: TextView, val l: Int, var r: Int): BaseAdapter() {
    override fun getCount(): Int {
        return size
    }
    override fun getItem(p0: Int): Any {
        return items.get(p0)
    }
    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.number_card, null)
        val button = view.findViewById<Button>(R.id.button)
        button.text = items.get(p0).toString()

        button.setOnClickListener {
            val cmp = items.get(p0)
            val comp = items.get(p0).toString()
            var URLfinal = eq.text.toString()
            var finalval = ""

            if (GameActivity.isIntegral) {
                if (URLfinal.length > 18) {
                    CoroutineScope(Dispatchers.Main).launch {
                        CoroutineScope(Dispatchers.Default).async {
                            URLfinal = EncodingURL(URLfinal, GameActivity.isIntegral, GameActivity.isDerive)
                            URLfinal = EncodingURL2(GetAPI(URLfinal, "area", l, r), GameActivity.x, GameActivity.isIntegral, GameActivity.isDerive)
                            finalval = GetAPI(URLfinal, "simplify", l, r)
                            if (finalval.equals(comp)) {
                                GameActivity.history.add(Pair(eq.text.toString(), 2*cmp))
                                GameActivity.score += 2*cmp
                                GameActivity.isIntegral = false
                                UpdateTargetNum(GameActivity.TargetItem, p0)
                                GameActivity.count = 0
                                eq.text = ""
                            }
                        }.await()
                    }
                }
            }
            else if (GameActivity.isDerive) {
                if (URLfinal.length > 6) {
                    CoroutineScope(Dispatchers.Main).launch {
                        CoroutineScope(Dispatchers.Default).async {
                            URLfinal = EncodingURL(URLfinal, GameActivity.isIntegral, GameActivity.isDerive)
                            URLfinal = EncodingURL2(GetAPI(URLfinal, "derive", l, r), GameActivity.x, GameActivity.isIntegral, GameActivity.isDerive)
                            finalval = GetAPI(URLfinal, "simplify", l, r)
                            if (finalval.equals(comp)) {
                                GameActivity.history.add(Pair(eq.text.toString(), 2*cmp))
                                GameActivity.score += 2*cmp
                                GameActivity.isDerive = false
                                UpdateTargetNum(GameActivity.TargetItem, p0)
                                GameActivity.count = 0
                                eq.text = ""
                            }
                        }.await()
                    }
                }
            }
            else {
                if (URLfinal.length > 0) {
                    CoroutineScope(Dispatchers.Main).launch {
                        CoroutineScope(Dispatchers.Default).async {
                            var Encoded = EncodingURL2(URLfinal, GameActivity.x, GameActivity.isIntegral, GameActivity.isDerive)
                            finalval = GetAPI(Encoded, "simplify", l, r)
                            if (finalval.equals(comp)) {
                                GameActivity.history.add(Pair(eq.text.toString(), cmp))
                                GameActivity.score += cmp
                                UpdateTargetNum(GameActivity.TargetItem, p0)
                                GameActivity.count = 0
                                eq.text = ""
                            }
                        }.await()
                    }
                }
            }
        }
        return view
    }
}