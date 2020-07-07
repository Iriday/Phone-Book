package phonebook

import kotlin.math.min
import kotlin.math.sqrt

fun <T> linearSearch(data: List<T>, target: T): Int {
    for ((i, str) in data.withIndex()) {
        if (str == target) {
            return i
        }
    }
    return -1
}

fun <T> linearSearchBackward(data: List<T>, target: T, leftIndexExcl: Int = -1, rightIndexIncl: Int = data.lastIndex): Int {
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

fun binarySearch(data: List<String>, target: String) = binarySearch(data, target, 0, data.lastIndex) // needed to create method reference

fun binarySearch(data: List<String>, target: String, left: Int = 0, right: Int = data.lastIndex): Int {
    var r = right
    var l = left

    while (l <= r) {
        val mid = l + (r - l) / 2

        if (target == data[mid]) {
            return mid
        }
        if (target < data[mid]) {
            r = mid - 1
        } else {
            l = mid + 1
        }
    }
    return -1
}
