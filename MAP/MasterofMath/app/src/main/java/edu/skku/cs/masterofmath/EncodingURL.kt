package edu.skku.cs.masterofmath


fun EncodingURL (equation: String, isIntegral: Boolean, isDerive: Boolean): String {
    // change +, -, *, /, ^, |, (, ) to encoding character
    var eq = equation
    if (isIntegral) eq = equation.substring(14, eq.length-2)
    if (isDerive) eq = equation.substring(4, eq.length)

    var URL = ""

    for (i in 0..eq.length-1) {
        when (eq[i]) {
            '+' -> URL = "$URL%2B"
            '/' -> URL = "$URL(over)"
            ' ' -> URL = "$URL%20"
            '^' -> URL = "$URL%5E"
            else -> URL += eq[i]
        }
    }

    return URL
}

fun EncodingURL2 (equation: String, x: Int, isIntegral: Boolean, isDerive: Boolean): String {
    // change +, -, *, /, ^, |, (, ) to encoding character
    var equation = equation

    var xx = x.toString()
    var URL = ""
    var URL1 = ""

    for (i in 0..equation.length-1) {
        if (equation[i] == 'x') {
            URL1 = URL1 + "(" + xx + ")"
        }
        else {
            URL1 += equation[i]
        }
    }

    for (i in 0..URL1.length-1) {
        when (URL1[i]) {
            '+' -> URL = "$URL%2B"
            '/' -> URL = "$URL(over)"
            ' ' -> URL = "$URL%20"
            '^' -> URL = "$URL%5E"
            else -> URL += URL1[i]
        }
    }

    return URL
}