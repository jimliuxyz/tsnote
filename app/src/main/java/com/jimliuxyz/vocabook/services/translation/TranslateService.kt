package com.jimliuxyz.vocabook.services.translation

import com.google.gson.JsonArray
import com.jimliuxyz.vocabook.constants.SupportLang
import com.jimliuxyz.vocabook.utils.doNetwork
import com.jimliuxyz.vocabook.utils.doUI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLEncoder
import java.util.concurrent.TimeUnit


/**
 * Created by jimliu on 2018/3/19.
 */
class TranslateService {

    private lateinit var api: GoogleTranslateApi

    private constructor() {

        val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)   // 設置連線Timeout
                .addInterceptor(HttpLoggingInterceptor().setLevel(Level.BASIC))
                .build()

        var retrofit = Retrofit.Builder()
                .baseUrl("https://translate.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        api = retrofit.create<GoogleTranslateApi>(GoogleTranslateApi::class.java!!)
    }

    companion object {
        val instance = TranslateService()
    }

    fun translate(srcLangCode: String, targetLangCode: String, query: String, callback: (String?) -> Unit) {
        doNetwork {
            val call = api.translate(
                    SupportLang.toGoogleTranslate(srcLangCode),
                    SupportLang.toGoogleTranslate(targetLangCode),
                    URLEncoder.encode(query, "utf-8"))

            try {
                val res = call.execute().body()
                var ans = ((res?.get(0) as? JsonArray)?.get(0) as? JsonArray)?.get(0)?.asString
                doUI {
                    callback(ans)
                }
            } catch (exp: Exception) {
                exp.printStackTrace()
            }
        }
    }

}
