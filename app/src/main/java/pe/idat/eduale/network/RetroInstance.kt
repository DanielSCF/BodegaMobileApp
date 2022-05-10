package pe.idat.eduale.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {
    val baseURL = "http://10.0.2.2:8070/"

    fun getRetroInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRetroClientInstance(): Retrofit {
        val gson= GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}