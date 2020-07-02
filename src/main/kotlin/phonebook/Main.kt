package phonebook

import kotlin.system.measureTimeMillis

fun main() {
    run()
}

fun run() {
    val phonesAndNames = readFile("src/main/kotlin/phonebook/test_samples/directory.txt")
    val targetNames = readFile("src/main/kotlin/phonebook/test_samples/find.txt")
    val namesFull = phonesAndNames.map { v -> v.substring(v.indexOf(' ') + 1) }

    run(namesFull, targetNames, ::linearSearch, "linearSearch")
    run(namesFull.sorted(), targetNames, ::jumpSearch, "JumpSearch")
}

fun run(phonesAndNames:List<String>, names:List<String>, searchAlg: ( List<String>, String) -> Int, algName: String){
    println("Start searching ($algName)...")
    val (resultsFound, elapsedTimeMillis) = testSearchPerfAndGetTimeAndResultsFound(phonesAndNames, names, searchAlg)

    val (min, sec, ms) = millisToMinSecMillis(elapsedTimeMillis)
    println(createOutputStr(resultsFound, names.size.toLong(), min, sec, ms))
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

fun createOutputStr(resultsFound: Long, results: Long, min: Long, sec: Long, ms: Long): String {
    return "Found $resultsFound / $results entries. Time taken: $min min. $sec sec. $ms ms."
}
