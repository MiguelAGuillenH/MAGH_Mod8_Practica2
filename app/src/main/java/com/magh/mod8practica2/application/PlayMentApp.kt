package com.magh.mod8practica2.application

import android.app.Application
import com.magh.mod8practica2.data.PlayMentRepository
import com.magh.mod8practica2.data.remote.RetrofitHelper

class PlayMentApp: Application() {

    private val retrofit by lazy{
        RetrofitHelper().getRetrofit()
    }

    val repository by lazy {
        PlayMentRepository(retrofit)
    }

}