package com.magh.mod8practica2.data.remote

import com.magh.mod8practica2.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {

    fun getRetrofit(): Retrofit {

        //Instanciación de inspector, para monitorear lo que Retrofit envia/recibe en Logcat
        /*val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder().apply{
            addInterceptor(interceptor)
        }.build()*/

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            //.client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

}