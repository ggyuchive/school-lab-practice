package edu.skku.cs.pa2

import android.content.Context
import android.content.Intent
import android.media.Image
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout

class GridViewAdapter(val context : Context, val size: Int, val items:ArrayList<Int>,
                      val curx: Int, val cury: Int, val curdir: Int, val ishint: Boolean, val hintx: Int, val hinty: Int): BaseAdapter() {

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
        val view: View = inflater.inflate(R.layout.maze_cell, null)
        val whiteview = view.findViewById<ImageView>(R.id.whiteImageView)
        val objectview = view.findViewById<ImageView>(R.id.objectImageView)

        val mgr = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val params = whiteview.layoutParams as ConstraintLayout.LayoutParams

        val displayMetrics = DisplayMetrics()
        mgr.defaultDisplay.getMetrics(displayMetrics)

        val dpi = displayMetrics.densityDpi

        val marginDp = ((3 * dpi).toDouble() / 160).toInt()
        params.width = (350/Math.sqrt(size.toDouble()) * dpi / 160).toInt()
        params.height = params.width
        val ind = items.get(p0)

        if (((ind shr 0) and 1) == 1) { // right
            params.width -= marginDp
            params.rightMargin = marginDp
        }
        if (((ind shr 1) and 1) == 1) { // bottom
            params.height -= marginDp
            params.bottomMargin = marginDp
        }
        if (((ind shr 2) and 1) == 1) { // left
            params.width -= marginDp
            params.leftMargin = marginDp
        }
        if (((ind shr 3) and 1) == 1) { // top
            params.height -= marginDp
            params.topMargin = marginDp
        }
        whiteview.layoutParams = params

        val sz = Math.sqrt(size.toDouble()).toInt()
        // hint image add
        if (ishint) {
            if (p0 == hintx*sz+hinty) {
                objectview.setImageResource(R.drawable.hint)
            }
        }
        // goal image add
        if (p0 == size-1) {
            objectview.setImageResource(R.drawable.goal)
        }
        // cur image add
        if (p0 == curx*sz+cury) {
            objectview.setImageResource(R.drawable.user)
            objectview.rotation = 90f*curdir
        }
        // if finish
        if (curx*sz+cury == size-1) {
            Toast.makeText(context, "Finish!", Toast.LENGTH_SHORT).show()
        }
        return view
    }
}