package phonebook

import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

fun main() {
    run()
}

fun run() {
    val phonesAndNames = readFile("src/main/kotlin/phonebook/test_samples/directory.txt")
    val targetNames = readFile("src/main/kotlin/phonebook/test_samples/find.txt")
    val namesFull = phonesAndNames.map { v -> v.substring(v.indexOf(' ') + 1) }

    val time = searchTest(namesFull, targetNames, ::linearSearch, "linear search")
    println()
    sortAndSearchTest(namesFull, targetNames, ::bubbleSort, ::jumpSearch, "bubble sort", "jump search", time, ::linearSearch, "linear search")
    println()
    sortAndSearchTest(namesFull, targetNames, ::quickSort, ::binarySearch, "quick sort", "binary search", time, ::linearSearch, "linear search")
    println()
    hashTableCreationAndSearchTest(phonesAndNames, targetNames)
}

fun searchTest(data:List<String>, targetData:List<String>, searchAlg: (List<String>, String) -> Int, algName: String): Long{
    println("Start searching ($algName)...")
    val (resultsFound, elapsedTimeMillis) = testSearchPerfAndGetTimeAndResultsFound(data, targetData, searchAlg)

    val (min, sec, ms) = millisToMinSecMillis(elapsedTimeMillis)
    println(createOutputStr(resultsFound, targetData.size.toLong(), min, sec, ms))
    return elapsedTimeMillis
}

fun sortAndSearchTest(data: List<String>, targetData: List<String>, sortAlg: (MutableList<String>) -> (Unit), searchAlg: (List<String>, String) -> Int, sortAlgName:String, searchAlgName: String, fallbackSearchAlgTime: Long, fallbackSearchAlg: (List<String>, String) -> Int, fallbackSearchAlgName: String){
    val mutableData = data.toMutableList()
    var sortTimeMillis = 0L
    var fallback = false

    println("Start searching ($sortAlgName + $searchAlgName)...")
    val startTime = System.currentTimeMillis()
    val thread = thread(start = true) { sortTimeMillis = testSortPerf(mutableData, sortAlg) }
    thread.join(fallbackSearchAlgTime * 10)
    val endTime = System.currentTimeMillis()
    if (thread.isAlive) {
        thread.stop()
        fallback = true
        sortTimeMillis = endTime - startTime
    }

    val (resultsFound, searchTimeMillis) =
        if (!fallback) testSearchPerfAndGetTimeAndResultsFound(mutableData, targetData, searchAlg)
        else testSearchPerfAndGetTimeAndResultsFound(data, targetData, fallbackSearchAlg)

    val (min, sec, ms) = millisToMinSecMillis(sortTimeMillis + searchTimeMillis)
    val (sortMin, sortSec, sortMs) = millisToMinSecMillis(sortTimeMillis)
    val (searchMin, searchSec, searchMs) = millisToMinSecMillis(searchTimeMillis)
    println(createOutputStr(resultsFound, targetData.size.toLong(), min, sec, ms))
    println("Sorting time: $sortMin min. $sortSec sec. $sortMs ms." + if (fallback) " - STOPPED, moved to $fallbackSearchAlgName" else "")
    println("Searching time: $searchMin min. $searchSec sec. $searchMs ms.")
}

fun hashTableCreationAndSearchTest(phonesAndNames: List<String>, targetData: List<String>) {
    val data = HashMap<String, String>()
    println("Start searching (hash table)...")
    val creationTime = measureTimeMillis {
        for (line in phonesAndNames) {
            val sepIndex = line.indexOf(' ')
            data[line.substring(sepIndex + 1)] = line.substring(0, sepIndex)
        }
    }
    var resultsFound = 0L
    val searchTime = measureTimeMillis {
        for (s in targetData) {
            resultsFound += if (data[s] != null) 1 else 0
        }
    }

    val (min, sec, ms) = millisToMinSecMillis(creationTime + searchTime)
    val (creationMin, creationSec, creationMs) = millisToMinSecMillis(creationTime)
    val (searchMin, searchSec, searchMs) = millisToMinSecMillis(searchTime)
    println(createOutputStr(resultsFound, targetData.size.toLong(), min, sec, ms))
    println("Creating time: $creationMin min. $creationSec sec. $creationMs ms.")
    println("Searching time: $searchMin min. $searchSec sec. $searchMs ms.")
}

fun testSearchPerfAndGetTimeAndResultsFound(data: List<String>, search: List<String>, searchAlg: (List<String>, String) -> Int ): LongArray {
    var resultsFound = 0L
    val elapsedTimeMillis = measureTimeMillis {
        for (s in search) {
            resultsFound += if (searchAlg(data, s) != -1) 1 else 0
        }
    }
    return longArrayOf(resultsFound, elapsedTimeMillis)
}

fun testSortPerf(data: MutableList<String>, sortAlg: (MutableList<String>) -> (Unit)): Long {
    return measureTimeMillis { sortAlg(data) }
}

fun createOutputStr(resultsFound: Long, results: Long, min: Long, sec: Long, ms: Long): String {
    return "Found $resultsFound / $results entries. Time taken: $min min. $sec sec. $ms ms."
}
