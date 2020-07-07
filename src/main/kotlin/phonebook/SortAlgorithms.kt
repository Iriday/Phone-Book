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

fun quickSort(data: MutableList<String>) {
    fun swap(data: MutableList<String>, a: Int, b: Int) {
        val temp = data[a]
        data[a] = data[b]
        data[b] = temp
    }

    fun partition(data: MutableList<String>, left: Int, right: Int): Int {
        val pivot = data[right]
        var partitionIndex = left

        for (i in left until right) {
            if (data[i] <= pivot) {
                swap(data, partitionIndex, i)
                partitionIndex++
            }
        }
        swap(data, partitionIndex, right)
        return partitionIndex
    }

    fun quickSort(data: MutableList<String>, left: Int, right: Int) {
        if (left < right) {
            val pivotIndex = partition(data, left, right)

            quickSort(data, left, pivotIndex - 1)
            quickSort(data, pivotIndex + 1, right)
        }
    }

    quickSort(data, 0, data.lastIndex)
}
