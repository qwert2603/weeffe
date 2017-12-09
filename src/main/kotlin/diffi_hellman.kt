import java.math.BigInteger
import java.util.*

private fun powMod(n: BigInteger, p: BigInteger, mod: BigInteger): BigInteger {
    var res = BigInteger.ONE

    var cp = BigInteger.ZERO
    while (cp < p) {
        res = res * n % mod
        cp += BigInteger.ONE
    }

    return res
}

private class M(
        val P: BigInteger,
        val G: BigInteger,
        private val priv: BigInteger
) {
    lateinit var anthPubl: BigInteger

    fun publ(): BigInteger = powMod(G, priv, P)

    fun key(): BigInteger = powMod(anthPubl, priv, P)
}

fun main(args: Array<String>) {
    val r = Math.abs(Random().nextLong()) / 2
    println(r)
    val P = BigInteger.valueOf(Long.MAX_VALUE / 2 + r)
    val G = BigInteger.valueOf(3L)

    val alice = M(P, G, BigInteger.valueOf(643378L))
    val bob = M(P, G, BigInteger.valueOf(654323L))

    alice.anthPubl = bob.publ()
    bob.anthPubl = alice.publ()

    println(alice.key())
    println(bob.key())
}