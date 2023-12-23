package edu.skku.cs.masterofmath

import kotlin.random.Random

// return random 1~9
fun ResetX (): Int {
    return Random.nextInt(1, 10)
}

// return random 1~9 * 4, except x
fun ResetUseNum (): ArrayList<Int> {
    var ret = arrayListOf(1, 1, 1, 1)
    val numbers = (1..9).toList()
    val selectedNumbers = numbers.shuffled().take(4)

    for (i in 0..3) {
        ret[i] = selectedNumbers[i]
    }
    return ret
}

// return random 0~2
fun ChooseIntegral (): Int {
    return Random.nextInt(1, 4)
}

// return random 10~50 * 4
fun ResetTargetNum (): ArrayList<Int> {
    var ret = arrayListOf(1, 1, 1, 1)
    val numbers = (10..50).toList()
    val selectedNumbers = numbers.shuffled().take(4)

    for (i in 0..3) {
        ret[i] = selectedNumbers[i]
    }
    return ret
}

// update target num in ind (0~3)
fun UpdateTargetNum (arr: ArrayList<Int>, ind: Int): ArrayList<Int> {
    var ret = arr
    var tag = 0
    var upd = Random.nextInt(10, 50)

    while (tag == 0) {
        tag = 1
        upd = Random.nextInt(10, 50)
        for (i in 0..3) {
            if (upd == ret[i]) tag = 0
        }
    }
    ret[ind] = upd
    return ret
}