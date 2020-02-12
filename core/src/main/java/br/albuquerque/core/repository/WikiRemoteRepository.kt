package br.albuquerque.core.repository

import br.albuquerque.core.BuildConfig
import br.albuquerque.core.network.Paths
import br.albuquerque.core.network.WikiException
import br.albuquerque.core.network.WikiInterceptor
import br.albuquerque.core.network.WikiResult
import com.facebook.stetho.okhttp3.StethoInterceptor
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class WikiRemoteRepository : CoroutineScope by CoroutineScope(Dispatchers.IO) {

    companion object {

        val okHttpClient: OkHttpClient = let {

            val loggingLevel = HttpLoggingInterceptor.Level.BODY

            OkHttpClient.Builder().apply {

                connectTimeout(10, TimeUnit.SECONDS)
                writeTimeout(60, TimeUnit.SECONDS)
                readTimeout(60, TimeUnit.SECONDS)

                addInterceptor(WikiInterceptor())

                if (BuildConfig.DEBUG) {

                    val loggingInterceptor = HttpLoggingInterceptor()
                    loggingInterceptor.level = loggingLevel

                    addInterceptor(loggingInterceptor)
                    addNetworkInterceptor(StethoInterceptor())
                }
            }.build()
        }
    }

    val requestMap: HashMap<String, Job> = hashMapOf()

    fun getRetrofitBuilder(url: String = Paths.BASE_URL): Retrofit {

        return Retrofit.Builder().apply {
            baseUrl(url)
            addConverterFactory(GsonConverterFactory.create())
            client(okHttpClient)
        }.build()
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun <API, T> executeRequest(api: API, avoidLogout: Boolean = false, request: suspend API.() -> T): WikiResult<T> {

        val requestKey = request.toString()
        var repositoryResult: WikiResult<T>? = null

        requestMap[requestKey]?.apply { if (isActive) cancelAndJoin() }
        requestMap[requestKey]

        coroutineScope {

            launch {

                repositoryResult = try {
                    WikiResult.Success(api.request())
                } catch (e: Exception) {

                    var exception = WikiException(message = e.message, cause = e.cause)

                    (e as? HttpException)?.response()?.errorBody()?.let { response ->
                        try{
                            val msg = JSONObject(response.string()).getString("error_message")
                            exception = WikiException(message = msg, cause = e.cause)
                        } catch (e: java.lang.Exception) { }
                    }

                    if(e is HttpException) exception.code = e.code()

                    WikiResult.Failure(exception)
                }
            }.join()
        }

        return repositoryResult!!
    }

}