package ndn.base.nguyen.di

import android.app.Application
import ndn.base.nguyen.data.source.remote.HandyApi
import com.google.gson.Gson
import ndn.base.nguyen.BuildConfig
import ndn.base.nguyen.data.source.remote.api.middleware.InterceptorImpl
import ndn.base.nguyen.data.source.remote.api.middleware.RxErrorHandlingCallAdapterFactory
import ndn.base.nguyen.data.source.repositories.TokenRepository
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideOkHttpCache(androidApplication()) }
    single { provideInterceptor(get()) }
    single { provideOkHttpClient(get(), get()) }
    single { provideRetrofit(get(), get()) }
    single { provideNameApi(get()) }
}

fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(
                    RxErrorHandlingCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
}

fun provideOkHttpCache(app: Application): Cache {
    val cacheSize: Long = 10 * 1024 * 1024 // 10 MiB
    return Cache(app.cacheDir, cacheSize)
}

fun provideInterceptor(tokenRepository: TokenRepository): Interceptor {
    return InterceptorImpl(tokenRepository)
}

fun provideOkHttpClient(cache: Cache, interceptor: Interceptor): OkHttpClient {
    val httpClientBuilder = OkHttpClient.Builder()
    httpClientBuilder.cache(cache)
    httpClientBuilder.addInterceptor(interceptor)

    httpClientBuilder.readTimeout(
        NetworkConstants.READ_TIMEOUT, TimeUnit.SECONDS)
    httpClientBuilder.writeTimeout(
        NetworkConstants.WRITE_TIMEOUT, TimeUnit.SECONDS)
    httpClientBuilder.connectTimeout(
        NetworkConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
        val logging = HttpLoggingInterceptor()
        httpClientBuilder.addInterceptor(logging)
        logging.level = HttpLoggingInterceptor.Level.BODY
    }

    return httpClientBuilder.build()
}

fun provideNameApi(retrofit: Retrofit): HandyApi {
    return retrofit.create(HandyApi::class.java)
}

object NetworkConstants {
    const val READ_TIMEOUT: Long = 30
    const val WRITE_TIMEOUT: Long = 30
    const val CONNECTION_TIMEOUT: Long = 30
}