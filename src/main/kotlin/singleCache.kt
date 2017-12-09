import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.security.Timestamp
import java.util.concurrent.*

@Synchronized
fun sout(s: Any) = println("${System.currentTimeMillis()} ${Thread.currentThread()} $s")

object L

fun main(args: Array<String>) {

    val publishSubject = PublishSubject.create<Int>()

    publishSubject
            .observeOn(Schedulers.from(Executors.newSingleThreadScheduledExecutor()))
            .scan(0) { i, other ->
                sout("scan b $i $other")
                Thread.sleep(1000)
                (i + other).also { sout("scan e $i $other") }
            }.subscribe { sout(it) }
    publishSubject
            .observeOn(Schedulers.from(Executors.newSingleThreadExecutor()))
            .scan(0) { i, other ->
                sout("scan b $i $other")
                Thread.sleep(1000)
                (i + other).also { sout("scan e $i $other") }
            }.subscribe { sout(it) }

    (1..3).forEach {
//                Thread({
        sout("publishSubject.onNext($it)")
        publishSubject.onNext(it)
//        }).start()
    }


    Thread.sleep(10000)
}