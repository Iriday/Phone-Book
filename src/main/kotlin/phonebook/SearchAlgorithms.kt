package phonebook

fun linearSearch(data: List<String>, search: String): Int {
    for ((i, str) in data.withIndex()) {
        if (str.contains(search)) {
            return i
        }
    }
    return -1
}
