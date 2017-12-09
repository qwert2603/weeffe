fun main(args: Array<String>) {
    val divs = mutableListOf(
            listOf(0),
            listOf(1),
            listOf(2)
    )
    (3..144).forEach { i ->
        for (it in 2..i / 2) {
            if (i % it == 0) {
                divs.add(divs[it] + divs[i / it])
                return@forEach
            }
        }
        divs.add(listOf(i))
    }
    for (i in 1..23) {
        for (j in 1..12) {
            val message: String
            if (i <= 23 - j * 2 + 1) message = "" else {
                val index = j * j + 23 - (j - 1) * 2 - i
//                val index = j * j + (i - 23)
                message = "$index ${divs[index]}"
            }
            print(message + ((1..26 - message.length).map { " " }.reduce { s1, s2 -> s1 + s2 }))
        }
        println()
    }
}