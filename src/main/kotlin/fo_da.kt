import io.reactivex.Single
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

data class R(
        val objects: List<E>
)

data class E(
        val id: Int,
        val title: String,
        val lat: Double,
        val lan: Double
)

interface Rest {
    @Multipart
    @POST("index.php?option=com_yandex_maps&task=load")
    fun getFilials(
            @Part("forse_id") forse_id: RequestBody = RequestBody.create(MediaType.parse("text/plain"), "0"),
            @Part("map_id") map_id: RequestBody = RequestBody.create(MediaType.parse("text/plain"), "1"),
            @Part("offset") offset: RequestBody = RequestBody.create(MediaType.parse("text/plain"), "0"),
            @Part("limit") limit: RequestBody = RequestBody.create(MediaType.parse("text/plain"), "500"),
            @Part("bound%5B0%5D%5B%5D") q1: RequestBody = RequestBody.create(MediaType.parse("text/plain"), "53.342146604327006"),
            @Part("bound%5B0%5D%5B%5D") q2: RequestBody = RequestBody.create(MediaType.parse("text/plain"), "74.21133311453617"),
            @Part("bound%5B1%5D%5B%5D") q3: RequestBody = RequestBody.create(MediaType.parse("text/plain"), "58.25987730670692"),
            @Part("bound%5B1%5D%5B%5D") q4: RequestBody = RequestBody.create(MediaType.parse("text/plain"), "87.15322764578617")
    ): Single<ResponseBody>
}

fun main(args: Array<String>) {
    val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor { message -> sout(message) }.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    val rest: Rest = Retrofit.Builder()
            .baseUrl("https://www.mfc-nso.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(Rest::class.java)

    rest.getFilials()
            .subscribe { t1, t2 ->
                println(t1.string())
//                t1.objects.sortedBy { it.id }.forEach { println(it) }
            }
}