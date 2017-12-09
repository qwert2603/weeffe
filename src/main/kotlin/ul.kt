import java.lang.Math.*

fun sqr(i: Int) = i * i
fun sqr(i: Long) = i * i
fun qub(i: Long) = i * i * i
fun isSqr(i: Int) = sqr(sqrt(i.toDouble()).toInt()) == i
fun isSqr(i: Long) = sqr(sqrt(i.toDouble()).toLong()) == i
fun sqrt(i: Int): Int {
    if (!isSqr(i)) throw IllegalArgumentException()
    return sqrt(i.toDouble()).toInt()
}
fun sqrt(i: Long): Long {
    if (!isSqr(i)) throw IllegalArgumentException()
    return sqrt(i.toDouble()).toLong()
}

fun main(args: Array<String>) {
    val N = (11L * 101L).let { it * it * it  * it }

    val r = sqrt(N.toDouble()).toInt()
    val fi = 1.0 * (N - r * r) / (2 * r + 1)

    val q = (fi * (2 * (r - 1) + 1)).toLong() + (r - 1) * (r - 1)
    for (i in q - 1 downTo 1) {
        val D = sqr(i - N - 1) - 4 * N
        println(sqrt(D.toDouble()))
        if (isSqr(D)) {
            val b = N + 1 - i
            val sqrtD = sqrt(D)
            val x1 = (b - sqrtD) / 2
            val x2 = (b + sqrtD) / 2
            println("$i\t${q - i}\t$sqrtD\t$x1\t$x2")
            break
        }
    }
}