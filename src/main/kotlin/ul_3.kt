import org.apache.commons.math3.complex.Complex

fun Double.isNearInt() = Math.abs(this).rem(1).let { if (it < 0.5) it else it - 1.0 } in -0.00001..0.00001

operator fun Complex.plus(anth: Complex): Complex = this.add(anth)
operator fun Complex.minus(anth: Complex): Complex = this.subtract(anth)
operator fun Complex.times(anth: Complex): Complex = this.multiply(anth)
operator fun Complex.div(anth: Complex): Complex = this.divide(anth)
fun pow(c: Complex, p: Double): Complex {
    if (c == Complex.ZERO) return Complex.ZERO
    return c.pow(p)
}

fun sqrt(c: Complex): Complex = pow(c, 0.5)
operator fun Complex.unaryMinus(): Complex = negate()

fun solve_3(A: Int, B: Int, C: Int, D: Int): List<Complex> {
    val B1 = Complex(B.toDouble()) / Complex(A.toDouble())
    val C1 = Complex(C.toDouble()) / Complex(A.toDouble())
    val D1 = Complex(D.toDouble()) / Complex(A.toDouble())

    val p: Complex
    val q: Complex
    if (B1 != Complex.ZERO) {
        p = (Complex(3.0) * C1 - B1 * B1) / (Complex(3.0))
        q = (Complex(2.0) * B1 * B1 * B1 - Complex(9.0) * B1 * C1 + Complex(27.0) * D1) / Complex(27.0)
    } else {
        p = C1
        q = D1
    }

    val Q = pow(p / Complex(3.0), 3.0) + pow(q / Complex(2.0), 2.0)

    val a = (-q / Complex(2.0) + sqrt(Q)).let { pow(it, 1.0 / 3.0) }
    val b = (-q / Complex(2.0) - sqrt(Q)).let { pow(it, 1.0 / 3.0) }

    val s1 = -(a + b) / Complex(2.0)
    val s2 = Complex.I * (a - b) / Complex(2.0) * Complex(Math.sqrt(3.0))
    val w = -B1 / Complex(3.0)
    val x1 = a + b + w
    val x2 = s1 + s2 + w
    val x3 = s1 - s2 + w
    return listOf(x1, x2, x3)
}


fun main(args: Array<String>) {
    val N = 853 * 99761

    val r = Math.pow(N.toDouble(), 1.0 / 3.0).toInt()
    val fi = 1.0 * (N - r * r * r) / (3 * r * r + 3 * r + 1)

    val rq = r - 1
    val q = (fi * (3 * rq * rq + 3 * rq + 1)).toInt() + rq * rq * rq
    println("q = $q")

    for (i in q downTo 1) {
        solve_3(-2, 3, N - i - 1, -N)
                .also {
                    //                    if (i == q || it.any { it.real.isNearInt() }) {
                    if (i % 1000 == 0) {
                        println(it.map { it.real }.let { "$i\t${it[0]}\t${it[1]}\t${it[2]}" })
                    }
                }
                .forEach {
//            println("solve_3 $i $it")
//            println("${it.real}")
                    if (it.imaginary in -0.00001..0.00001 && it.real.isNearInt()) {
                        val intX = Math.round(it.real).toInt()
                        println("return")
                        println("x = $it")
                        println("i = $i (step #${q - i + 1})")
                        val d = -3 * it.real * it.real + 3 * it.real + N - i - 1
                        println("d = $d")
                        if (intX > 0 && N % intX == 0) return
                    }
                }
    }


}