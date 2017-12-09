import io.reactivex.Completable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicReference

class Q<I> {
     data class TaskId(private val id: Any?)

    val tasks = ConcurrentHashMap<TaskId, String>()

    fun d(i: I?, s: String) {
        tasks.put(TaskId(i), s)
    }

    fun r(i: I) = tasks.remove(TaskId(i))
}

fun main(args: Array<String>) {
    val q = Q<Long>()

    q.d(3, "a")
    q.d(2, "c")
    q.d(3, "b")
    println(q.r(2))
    println(q.r(3))
    println(q.r(4))
}