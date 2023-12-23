package edu.skku.cs.masterofmath

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import java.io.IOException

fun GetAPI(equation: String, type: String, l: Int, r: Int): String  {
     // type: derive, simplify, area
    val host = "https://newton.now.sh/api/v2/"
    var path = "$type/$equation"
    if (type == "area") {
        path = "$type/$l:$r|$equation"
    }

    var req = Request.Builder().url(host+path).build()

    var ret = ""
    val client = OkHttpClient()
    client.newCall(req).execute().use {
        response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        val str = response.body!!.string()
        val data = Gson().fromJson(str, Newton::class.java)
        ret = data.result.toString()
    }
    return ret
}
