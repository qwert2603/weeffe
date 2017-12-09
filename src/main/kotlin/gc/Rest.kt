package gc

import io.reactivex.Single
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.CookieManager

interface Rest {
    companion object {

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor { message -> print(message) }.setLevel(HttpLoggingInterceptor.Level.BODY))
                .cookieJar(JavaNetCookieJar(CookieManager()))
                .build()

        val rest: Rest = Retrofit.Builder()
                .baseUrl("http://geocode-maps.yandex.ru/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(Rest::class.java)
    }

    @GET("1.x/")
    fun getXMLReverseGeoCodeURL(
            @Query("format") format: String = "json",
            @Query("results") results: Int = 1,
            @Query("kind") kind: String = "house",
            @Query("geocode") geocode: String
    ): Single<ResponseBody>
}