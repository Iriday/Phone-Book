package phonebook

fun bubbleSort(data: MutableList<String>) {
    for (i in 0 until data.lastIndex) {
        for (j in 0 until data.lastIndex - i) {
            if (data[j] > data[j + 1]) {
                val temp = data[j + 1]
                data[j + 1] = data[j]
                data[j] = temp
            }
        }
    }
}
