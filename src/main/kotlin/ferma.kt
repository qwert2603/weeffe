fun sqrtI(l: Long): Long {
    val sqrt = Math.sqrt(l.toDouble()).toLong()
    if (isFullSqr(l)) return sqrt
    return sqrt + 1
}

fun isFullSqr(l: Long) = l == Math.sqrt(l.toDouble()).toLong().let { it * it }

fun main(args: Array<String>) {
    val N = 229_637L

    val BB = 25L

    var found = false
    for (i in 2L..BB) {
        if (N % i == 0L) {
            println("$N = $i * ${N / i} via BB")
            found = true
            break
        }
    }

    if (!found) {
        for (x in sqrtI(N)..N) {
            val qx = x * x - N
            if (isFullSqr(qx)) {
                val B = sqrtI(qx)
                val A = sqrtI(B * B + N)
                if (A - B == 1L) {
                    println("it's prime")
                } else {
                    println("$N = ${A + B} * ${A - B} step #${x - sqrtI(N) + 1}")
                }
                break
            }
        }
    }
}