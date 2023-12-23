package edu.skku.cs.masterofmath

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast

class OperationGridViewAdapter(val context : Context, val size: Int, val items: ArrayList<Drawable>, val eq: TextView,
                               var l: Int, var r: Int, var usenumgridview: GridView, var equationtextview: TextView): BaseAdapter() {

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
        val view: View = inflater.inflate(R.layout.operation_card, null)
        val button = view.findViewById<Button>(R.id.button)
        button.background = items.get(p0)

        var t = arrayListOf("+", "-", "*", "/", "(", ")", "erase", "reset", "x", "x^2", "derive", "integral")

        button.setOnClickListener {
            if (p0 == 0) button.setBackgroundResource(R.drawable.op1_r)
            if (p0 == 1) button.setBackgroundResource(R.drawable.op2_r)
            if (p0 == 2) button.setBackgroundResource(R.drawable.op3_r)
            if (p0 == 3) button.setBackgroundResource(R.drawable.op4_r)
            if (p0 == 4) button.setBackgroundResource(R.drawable.op5_r)
            if (p0 == 5) button.setBackgroundResource(R.drawable.op6_r)
            if (p0 == 6) button.setBackgroundResource(R.drawable.op7_r)
            if (p0 == 7) button.setBackgroundResource(R.drawable.op8_r)
            if (p0 == 8) button.setBackgroundResource(R.drawable.op9_r)
            if (p0 == 9) button.setBackgroundResource(R.drawable.op10_r)
            if (p0 == 10) button.setBackgroundResource(R.drawable.op11_r)
            if (p0 == 11) {
                if (r == 3) button.setBackgroundResource(R.drawable.op12_1_r)
                if (r == 4) button.setBackgroundResource(R.drawable.op12_2_r)
                if (r == 6) button.setBackgroundResource(R.drawable.op12_3_r)
            }

            Handler().postDelayed({
                button.background = items.get(p0)
            }, 100)

            if (p0 < 6 || p0 == 8 || p0 == 9) {
                if (GameActivity.count == 10) {
                    Toast.makeText(context, "10회 버튼을 모두 사용하셨습니다.", Toast.LENGTH_SHORT).show()
                }
                else {
                    if (GameActivity.isIntegral) {
                        eq.text = eq.text.toString().substring(0..eq.text.length - 4) + t[p0] + ")dx"
                    } else if (GameActivity.isDerive) {
                        eq.text = eq.text.toString().substring(0..eq.text.length - 2) + t[p0] + ")"
                    } else eq.text = eq.text.toString() + t[p0]
                    GameActivity.count++
                }
            }
            if (p0 == 6) {
                if (eq.text.isNotEmpty()) {
                    if (GameActivity.isIntegral) {
                        if (eq.text.length == 18) {eq.text = ""; GameActivity.isIntegral = false}
                        else eq.text =eq.text.toString().substring(0..eq.text.length-5) + ")dx"
                    }
                    else if (GameActivity.isDerive) {
                        if (eq.text.length == 6) {eq.text = ""; GameActivity.isDerive = false}
                        else eq.text = eq.text.toString().substring(0..eq.text.length-3) + ")"
                    }
                    else eq.text = eq.text.toString().substring(0, eq.text.length - 1)
                    GameActivity.count--
                }
            }
            // usenum reset
            if (p0 == 7) {
                GameActivity.UseNumItem = ResetUseNum()
                usenumgridview.adapter = UseNumGridViewAdapter(context, 4, GameActivity.UseNumItem, equationtextview)
                eq.text = ""
                GameActivity.count = 0
                GameActivity.isDerive = false
                GameActivity.isIntegral = false
            }
            if (p0 == 10) {
                if (GameActivity.isIntegral || GameActivity.isDerive) {
                    Toast.makeText(context, "미적분 연산을 이미 사용하셨습니다.", Toast.LENGTH_SHORT).show()
                }
                else {
                    eq.text = "d/dx(" + eq.text.toString() + ")"
                    GameActivity.isDerive = true
                    GameActivity.count++
                }
            }
            if (p0 == 11) {
                if (GameActivity.isIntegral || GameActivity.isDerive) {
                    Toast.makeText(context, "미적분 연산을 이미 사용하셨습니다.", Toast.LENGTH_SHORT).show()
                }
                else {
                    eq.text = "integral_{$l:$r}(" + eq.text.toString() + ")dx"
                    GameActivity.isIntegral = true
                    GameActivity.count++
                }
            }
        }

        return view
    }
}