import java.lang.Math.*
import java.awt.Color
import java.awt.Graphics
import java.awt.Rectangle
import javax.swing.JFrame

val s = 1000
val m = 0.18
val tr = 100

fun main(args: Array<String>) {

    val jFrame = object : JFrame() {
        override fun paint(g: Graphics) {
            super.paint(g)
//            g.d({ (it + 37) * sqr(it) * it }, Color.BLACK, 4.0)
//            g.d({ (it + 27) * sqr(it) }, Color.BLACK, 3.0)
//            g.d({ (it + 37) * sqr(it) }, Color.RED, 3.0)
//            g.d({ it * (sqr(it) + 138) }, Color.BLUE, 3.0)
            g.d({ ((pow(it.toDouble(), 1.9) + 113) * pow(it.toDouble(), 1.9)).toLong() }, Color.RED, 3.8)
//            g.d({ it }, Color.BLACK, 2.0)
//            g.d({ (it * it - 11) * it }, Color.BLUE, 3.0)
//            g.d({ (it * it + 11) * it }, Color.RED, 3.0)
//            g.d({ (it * it + 12) * it }, Color.ORANGE, 3.0)
//            g.d({ (it * it + 112) * it }, Color.GREEN, 3.0)
        }
    }
    jFrame.bounds = Rectangle(s + 300, s)
    jFrame.isVisible = true

}

fun Graphics.d(d: (Long) -> Long, color: Color, p: Double = 2.0) {
    this.color = color
    var px = 0.0
    var py = 0.0
    (0L..8000 step 1).forEach {
        val i = d(it)
        val r = pow(i.toDouble(), 1 / p)
        val toInt = r.toInt().toDouble()
        val fi = 2 * PI * (i.toDouble() - pow(toInt, p)) / (pow(toInt + 1, p) - pow(toInt, p))
//        println("$i $r $fi")
        val x = m * pow(i.toDouble(), 1 / p) * cos(fi)
        val y = m * pow(i.toDouble(), 1 / p) * sin(fi)
//        if (it > 390L/* && it % 10 == 9L*/) {
        drawString(i.toString(), s / 2 + x.toInt() + tr, s / 2 - y.toInt())
//        println("$p\t$it\t$i\t$r\t$fi\t${fi / (2 * PI)}")
//        }
        drawLine(s / 2 + px.toInt() + tr, s / 2 - py.toInt(), s / 2 + x.toInt() + tr, s / 2 - y.toInt())
        px = x
        py = y
    }
}