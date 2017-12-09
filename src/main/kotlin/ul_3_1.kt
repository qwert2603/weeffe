import java.lang.Math.*

fun main(args: Array<String>) {
    val A = 1.0
    val B = -6.0
    val C = 11.0
    val D = -6.0

    val p = (3.0 * A * C - B * B) / (3.0 * A * A)
    val q = (2.0 * B * B * B - 9.0 * A * B * C + 27.0 * A * A * D) / (27.0 * A * A * A)

    println("$p\t$q")

    val Q = pow(p / 3.0, 3.0) + pow(q / 2.0, 2.0)

    println("Q = $Q")

    val a = pow(-q / 2 + sqrt(Q), 1.0 / 3.0)
    val b = pow(-q / 2 - sqrt(Q), 1.0 / 3.0)

    val y1 = a + b
    println("y1 = $y1")

}