import java.util.*

fun nod(i: Int, j: Int): Int {
    if (i == j) return i
    val max = maxOf(i, j)
    val min = minOf(i, j)
    return nod(max - min, min)
}

private fun powMod(n: Long, p: Long, mod: Long): Long {
    var p = p
    var res: Long = 1
    var pow = n
    while (p > 0) {
        if (p and 1 == 1L) res = res * pow % mod
        pow = pow * pow % mod
        p /= 2
    }
    return res
}

fun freqTest(list: List<Boolean>): Double = list
        .map { if (it) 1 else -1 }
        .sum()
        .let { Math.abs(it) }.toDouble()
        .let { it / Math.sqrt(list.size.toDouble()) }

fun seqTest(list: List<Boolean>): Double {
    val n = list.size
    val pi = 1.0 / n * list.count { it }
    var d = 0
    for (i in 0 until n-1) {
        if (list[i] != list[i + 1]) {
            ++d
        }
    }
    val Vn = 1 + d
    return Math.abs(Vn - 2 * n * pi * (1 - pi)) / (2 * Math.sqrt(2.0 * n) * pi * (1 - pi))
}

fun main(args: Array<String>) {
    val x = mutableListOf<Boolean>()

    val p = 5503
    val q = 4789
    val N = p * q
    val f = (p - 1) * (q - 1)

    val ra = Random(45)
    for (w in 0 until 1000) {
        var k: Int
        do {
            k = ra.nextInt(f - 1) + 2
        } while ((nod(k, f) != 1))
        var u = ra.nextInt(N - 2) + 2

        for (i in 1..20) {
            u = powMod(u.toLong(), k.toLong(), N.toLong()).toInt()
        }
        x += u % 2 == 1
    }
    x.sout()

    val freqTest = freqTest(x)
    println(freqTest)
    println(freqTest < 1.82138636)

    val seqTest = seqTest(x)
    println(seqTest)
    println(seqTest < 1.82138636)


    val jr = (1..2000).map { ra.nextBoolean() }
    println(freqTest(jr))
    println(seqTest(jr))
}