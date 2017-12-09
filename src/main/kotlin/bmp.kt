import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.util.*

const val HEADER_SIZE = 54L
const val BITS_PER_CHAR = 8

fun <T> List<T>.divide(i: Int) = (0..(size - 1) / i).map { drop(it * i).take(i) }

fun List<Boolean>.toInt() = map { if (it) 1 else 0 }.reduce { acc, i -> acc * 2 + i }

fun Int.toBooleanList(size: Int) = (0 until size).map { (this shr it) and 1 == 1 }.reversed()

fun List<Char>.makeString() = map { it.toString() }.reduce { s1, s2 -> "$s1$s2" }

fun FileInputStream.readInts(count: Int = Int.MAX_VALUE): List<Int> {
    val res = mutableListOf<Int>()
    for (i in 0 until count) {
        val b = read()
        if (b == -1) break
        res += b
    }
    return res
}

fun readFile(name: String): String = FileInputStream(name).use {
    BufferedReader(InputStreamReader(it))
            .lineSequence()
            .reduce { s1, s2 -> "$s1$s2" }
}

fun show(file: String) {
    FileInputStream(file).use { fis ->
        fis.skip(HEADER_SIZE)
        fis.readInts(3000)
                .divide(3)
                .forEach { println("${it[2]}\t${it[1]}\t${it[0]}") }
    }
}

fun encode(
        inFile: String,
        text: String,
        outFile: String,
        lsb: Int = 1,
        pixelSelector: (Int) -> Int = { it }
) {
    FileInputStream(inFile).use { fis ->
        FileOutputStream(outFile).use { fos ->
            (0 until HEADER_SIZE).forEach { fos.write(fis.read()) }
            val bb = fis.readInts().toMutableList()
            text
                    .map { it.toInt() }
                    .map { it.toBooleanList(BITS_PER_CHAR) }
                    .flatten()
                    .divide(lsb)
                    .map { it.toInt() }
                    .forEachIndexed { index, b ->
                        val pixel = pixelSelector(index)
                        bb[pixel] = (bb[pixel] shr lsb shl lsb) + b
                    }
            fos.write(bb.map { it.toByte() }.toByteArray())
        }
    }
}

fun decode(
        inFile: String,
        lsb: Int = 1,
        count: Int = Int.MAX_VALUE,
        pixelSelector: (Int) -> Int = { it }
) = FileInputStream(inFile).use { fis ->
    fis.skip(HEADER_SIZE)

    val readInts = fis.readInts()
    (0..count)
            .map(pixelSelector)
            .map { readInts[it] }

//    fis.readInts(count)
            .map { it.toBooleanList(lsb) }
            .flatten()
            .divide(BITS_PER_CHAR)
            .map { it.toInt() }
            .map { it.toChar() }
            .makeString()
}

fun modify(
        inFile: String,
        outFile: String,
        lsb: Int = 1
) {
    FileInputStream(inFile).use { fis ->
        FileOutputStream(outFile).use { fos ->
            (0 until HEADER_SIZE).forEach { fos.write(fis.read()) }
            val bb = fis.readInts()
                    .map { it.toBooleanList(lsb).toInt() shl (8 - lsb) }

            fos.write(bb.map { it.toByte() }.toByteArray())
        }
    }
}


fun main(args: Array<String>) {

    val b = System.currentTimeMillis()

//    val R: (Int) -> Int = { it * 3 + 2 }
//    val G: (Int) -> Int = { it * 3 + 1 }
//    val B: (Int) -> Int = { it * 3 }
//    val RG: (Int) -> Int = { it + it / 2 + 1 }
//    val RB: (Int) -> Int = { it + (it + 1) / 2 }
//    val GB: (Int) -> Int = { it + it / 2 }

//    val r = Random(-356275197936213458)
//    val list = mutableListOf(26)
//    (0..3_000_000).forEach {
//        list.add(list.last() + if (r.nextBoolean()) 1 else 2)
//    }
//    val key: (Int) -> Int = { list[it] }

//    val lsb = 2


//    show("result.bmp")
//    val text = readFile("mice.txt")//.take(300)
//    encode("asd5.bmp", text, "asd5_en.bmp", lsb, key)
//    println(decode("asd5_en.bmp", lsb, 80000, key))
//    println(decode("asd5_en.bmp", lsb, 80000, key))

//    modify("asd5_en.bmp", "asd5_en_1.bmp", 1)
//    modify("asd5_en.bmp", "asd5_en_2.bmp", 2)
//    modify("asd5.bmp", "asd5_1.bmp", 1)
//    modify("asd5.bmp", "asd5_2.bmp", 2)

//    (1..5).forEach { modify("img/$it.bmp", "img/${it}_2.bmp", 2) }
    (1..5).forEach { modify("asd$it.bmp", "asd${it}_2_a.bmp", 2) }

    println("${(System.currentTimeMillis() - b) / 1000.0} s")
}