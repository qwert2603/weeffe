import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.Executors

fun main(args: Array<String>) {
    val publishSubject = PublishSubject.create<Int>()

    publishSubject
            .scan(0) { i, other ->
                sout("scan $i $other")
                Thread.sleep(1000)
                i + other
            }
            .subscribe { sout("subscribe $it") }

    (1..30).forEach {
        Thread { publishSubject.onNext(it) }.start()
    }


    Thread.sleep(10000)
}