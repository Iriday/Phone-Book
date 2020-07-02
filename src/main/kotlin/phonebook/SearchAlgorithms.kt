package phonebook

import kotlin.math.min
import kotlin.math.sqrt

fun linearSearch(data: List<String>, target: String): Int {
    for ((i, str) in data.withIndex()) {
        if (str.contains(target)) {
            return i
        }
    }
    return -1
}

fun  linearSearchBackward(data: List<String>, target: String, leftIndexExcl: Int = 0, rightIndexIncl: Int = data.lastIndex): Int {
    for (i in rightIndexIncl.downTo(leftIndexExcl + 1)) {
        if (data[i] == target) {
            return i
        }
    }
    return -1
}


fun jumpSearch(data: List<String>, target: String): Int {
    var currRight = 0
    var prevRight = 0

    if (data.isEmpty()) {
        return -1
    }
    if (data[currRight] == target) {
        return currRight
    }

    val jump = sqrt(data.size.toDouble()).toInt()

    while (currRight < data.size - 1) {
        currRight = min(data.size - 1, currRight + jump)

        if (target <= data[currRight]) {
            break
        }
        prevRight = currRight
    }
    if (currRight == data.size - 1 && target > data[currRight]) {
        return -1
    }
    return linearSearchBackward(data, target, prevRight, currRight)
}
