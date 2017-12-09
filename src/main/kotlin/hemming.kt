private val P2: List<Int> = (0..20).map { 1 shl it }

fun Boolean.toInt() = if (this) 1 else 0

fun String.toBooleanList() = this
        .map { it.toInt() }
        .map { it.toBooleanList(8) }
        .flatten()

fun List<Boolean>.anthToString() = divide(8).map { it.toInt().toChar().toString() }.reduce { c1, c2 -> "$c1$c2" }

fun List<Boolean>.removeCtrl(): List<Boolean> = this.filterIndexed { index, _ -> index != 0 && index !in P2 }

fun List<Boolean>.addCtrlZeros(): List<Boolean> {
    val result = this.toMutableList()
    result.add(0, false)
    var q = 0
    while (P2[q] < result.size) {
        result.add(P2[q], false)
        ++q
    }
    return result
}

fun calculateCtrl(list: List<Boolean>): List<Boolean> {
    val result = mutableListOf<Boolean>()
    var i = 0
    while (1 shl i < list.size) {
        var b = false
        list.forEachIndexed { index, q ->
            if (index != 0 && index shr i and 1 == 1 && q) {
                b = !b
            }
        }
        result += b
        ++i
    }
    return result
}

fun List<Boolean>.addCtrl(ctrl: List<Boolean>): List<Boolean> {
    return this.mapIndexed { index, b ->
        P2.indexOf(index).takeIf { it >= 0 }?.let { ctrl[it] } ?: b
    }
}

fun List<Boolean>.findError(): Int = calculateCtrl(this).reversed().toInt()

fun List<Boolean>.negate(index: Int): List<Boolean> = mapIndexed { i, b -> b xor (i == index) }

fun List<Boolean>.sout(s: String = "") {
    print(s + " ")
    forEach { print(it.toInt()) }
    println()
}

fun List<Boolean>.isCh() = map { if (it) 1 else 0 }.sum() % 2 == 1

fun List<Boolean>.isLastCorrect() = dropLast(1).isCh() == last()

fun String.divide(s: Int): List<String> {
    val res = mutableListOf<String>()
    var q = 0
    while (q < length) {
        res += substring(q, Math.min(length, q + s))
        q += s
    }
    return res
}

fun main(args: Array<String>) {
    "hello, earth!"
            .divide(2)
            .forEach {
                it
                        .toBooleanList()
                        .addCtrlZeros()
//                        .also { it.drop(1).sout("q") }
                        .let { it.addCtrl(calculateCtrl(it)) }
//                        .also { it.drop(1).sout("w") }
                        .negate(5)
//                        .also { println("r ${it.removeCtrl().anthToString()}") }
                        .let {
                            val e = it.findError()
                            println("e $e")
                            if (e == 0) it else it.negate(e)
                        }
                        .also { println("t ${it.removeCtrl().anthToString()}") }
            }


    /* "hello, earth!"
             .toBooleanList()
             .addCtrlZeros()
             .also { it.sout() }
             .let { it + it.isCh() }
             .negate(7)
             .negate(8)
             .let {
                 println("last correct == ${it.isLastCorrect()}")
                 val e = it.findError()
                 println("e $e")
                 if (e == 0) it else it.negate(e)
             }
             .also { println(it.removeCtrl().anthToString()) }*/
}