package phonebook

import java.io.File

fun readFile(filepath: String): List<String> {
    return File(filepath).readLines()
}

fun millisToMinSecMillis(millis: Long): LongArray {
    val min = millis / (60 * 1000)
    val sec = millis % (60 * 1000) / 1000
    val ms = millis % (60 * 1000) % 1000
    return longArrayOf(min, sec, ms)
}
