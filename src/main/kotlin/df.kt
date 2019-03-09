import java.math.BigInteger
import java.util.*


fun Long.toBi(): BigInteger = BigInteger.valueOf(this)
fun Int.toBi(): BigInteger = this.toLong().toBi()

fun pr(max: Int): List<Int> {
    val primes = ArrayList<Int>()

    for (i in 2 until max) {
        var isPrimeNumber = true

        for (j in 2 until i) {
            if (i % j == 0) {
                isPrimeNumber = false
                break
            }
        }

        if (isPrimeNumber) {
            primes.add(i)
        }
    }

    return primes
}

fun BigInteger.isSqr() = this.sqrt().let { it * it } == this

fun BigInteger.sqrt(): BigInteger {
    if (this < BigInteger.ZERO) throw IllegalArgumentException("Negative argument.")
    if (this == BigInteger.ZERO || this == BigInteger.ONE) return this
    val two = BigInteger.valueOf(2L)
    var y: BigInteger
    y = this.divide(two)
    while (y > this.divide(y)) {
        y = this.divide(y).add(y).divide(two)
    }
    return y
}

fun BigInteger.divAll(q: BigInteger): BigInteger = if (this % q != 0.toBi()) this else (this / q).divAll(q)

fun <T : Comparable<T>> Pair<T, T>.ordered() = if (first > second) second to first else this

fun diffMod(a: BigInteger, b: BigInteger, mod: BigInteger): BigInteger {
    val max = maxOf(a, b)
    val min = minOf(a, b)
    val result = minOf(max - min, min - max + mod)
    if (result < 0.toBi()) throw Exception("diffMod < 0")
    return result
}

data class SolveResult(val x: BigInteger, val y: BigInteger, val steps: Int)

fun solve(n: BigInteger, mod: BigInteger, diff: BigInteger, steps: Int): SolveResult? {

    for (i in 0 until steps) {
        val theDiff = diff + mod * i.toBi()

        val d = theDiff * theDiff + 4.toBi() * n

        if (!d.isSqr()) continue

        val x = (-theDiff - d.sqrt()) / BigInteger.valueOf(2)
        val y = (-theDiff + d.sqrt()) / BigInteger.valueOf(2)

        return SolveResult(x.abs(), y.abs(), i)
    }

    return if (diff < mod / 2.toBi()) solve(n, mod, mod - diff, steps) else null
}


data class DfResult(
        val solveResult: SolveResult,
        val steps: Int,
        val roots: Pair<BigInteger, BigInteger> = solveResult.x.divAll(2.toBi()) to solveResult.y.divAll(2.toBi())
)

fun df(
        _n: BigInteger,
        steps: Int,
        solveSteps: Int,
        multiM: Int,
        multiN: Int,
        www: Int = 0,
        wwwA: Boolean = false,
        qqq: Boolean = false
): DfResult? {
    println("df $_n $www $wwwA $qqq")

    var n = _n

    if (n % multiM.toBi() == 0.toBi()) {
        throw IllegalArgumentException()
    }

    var a: BigInteger
    var b: BigInteger
    var m: BigInteger

    when (multiM) {
        2 -> {
            m = 4.toBi()
            if (n % m == 1.toBi()) {
                a = 1.toBi()
                b = 1.toBi()
            } else {
                a = 1.toBi()
                b = 3.toBi()
            }
        }
        3 -> {
            m = 3.toBi()
            if (n % m == 1.toBi()) {
                a = 1.toBi()
                b = 1.toBi()
            } else {
                a = 1.toBi()
                b = 2.toBi()
            }
        }
        else -> throw IllegalArgumentException()
    }

    fun printy() {
        println("printy $n $m $a $b ${diffMod(a, b, m)}")
    }

    fun ch() {
        if (a * b % m != n % m) {
            throw Exception("smth is wrong!")
        }
    }

    for (i in 0 until steps) {
        printy()
        ch()

        solve(n, m, diffMod(a, b, m), solveSteps)
                ?.let { DfResult(it, i) }
                ?.takeIf { it.roots.first != 1.toBi() && it.roots.second != 1.toBi() }
                ?.let { return it }

        m *= multiM.toBi()

        if (a * b % m != n % m) {
            if (a > b) {
                b += m / multiM.toBi()
                if (a * b % m != n % m) b += m / multiM.toBi()
                b %= m
            } else {
                a += m / multiM.toBi()
                if (a * b % m != n % m) a += m / multiM.toBi()
                a %= m
            }
            printy()
            ch()
        }

        n *= multiN.toBi()

        if (i < www) {
            if (wwwA) {
                a = a * multiN.toBi() % m
            } else {
                b = b * multiN.toBi() % m
            }
        } else {
            if (i % 2 == (if (qqq) 1 else 0)) {
                a = a * multiN.toBi() % m
            } else {
                b = b * multiN.toBi() % m
            }
        }
    }

    return if (qqq && wwwA) {
        if (www == steps / 3) {
            null
        } else {
            df(_n, steps, solveSteps, multiM, multiN, www + 1, false, false)
        }
    } else {
        if (qqq) {
            df(_n, steps, solveSteps, multiM, multiN, www, true, false)
        } else {
            df(_n, steps, solveSteps, multiM, multiN, www, wwwA, true)
        }
    }
}

fun main(args: Array<String>) {

    println(solve(61771.toBi(), 3.toBi(), 0.toBi(), 1000))
    println(solve(123542.toBi(), 9.toBi(), 2.toBi(), 1000))
    println(solve(123542.toBi(), 27.toBi(), 11.toBi(), 10000))
    println(solve(247084.toBi(), 81.toBi(), 30.toBi(), 10000))
    return

    fun asserty(b: Boolean, s: String) {
        if (!b) throw Exception("smth is wrong! $s")
    }

    fun assertyDiffMod(a: BigInteger, b: BigInteger, mod: BigInteger, expected: BigInteger) {
        asserty(diffMod(a, b, mod) == expected, "assertyDiffMod $a $b $mod $expected")
    }

    fun assertyDiffMod(a: Long, b: Long, mod: Long, expected: Long) = assertyDiffMod(a.toBi(), b.toBi(), mod.toBi(), expected.toBi())

    val steps = 5
    val solveSteps = 17
    val multiM = 3
    val multiN = 2

    var maxSteps = -1
    var maxSolveSteps = -1

    fun assertyDf(a: BigInteger, b: BigInteger) {
        val df = df(a * b, steps, solveSteps, multiM, multiN)
        println(df)
        asserty(df?.roots?.ordered() == a to b, "assertyDf $a $b")
        df!!
        maxSteps = maxOf(maxSteps, df.steps)
        maxSolveSteps = maxOf(maxSolveSteps, df.solveResult.steps)
    }

    fun assertyDf(a: Long, b: Long) = assertyDf(a.toBi(), b.toBi())

    assertyDiffMod(3, 7, 8, 4)
    assertyDiffMod(7, 3, 8, 4)
    assertyDiffMod(3, 5, 8, 2)
    assertyDiffMod(5, 3, 8, 2)
    assertyDiffMod(1, 7, 8, 2)
    assertyDiffMod(7, 1, 8, 2)

//    println(df((37 * 61).toBi(), steps, solveSteps, multiM, multiN)!!)
//    println(df((11 * 71).toBi(), steps, solveSteps, multiM, multiN)!!)
//    println(df((37 * 73).toBi(), steps, solveSteps, multiM, multiN)!!)
//    println(df((41 * 67).toBi(), steps, solveSteps, multiM, multiN)!!)
//    println(df((101 * 149).toBi(), steps, solveSteps, multiM, multiN)!!)
//    println(df((11 * 47).toBi(), steps, solveSteps, multiM, multiN)!!)
//    println(df((11 * 113).toBi(), steps, solveSteps, multiM, multiN)!!)
    println(df((223 * 277).toBi(), steps, solveSteps, multiM, multiN)!!)

    val p = pr(300).filter { it > 10 }.map { it.toLong() }

    for (i in p) {
        for (j in p) {
            if (j < i) continue

//            assertyDf(i, j)
        }
    }

    println("maxSteps $maxSteps")
    println("maxSolveSteps $maxSolveSteps")
}