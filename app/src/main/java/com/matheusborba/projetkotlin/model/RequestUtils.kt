package com.matheusborba.projetkotlin.model

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit

sealed class ClientBuilder {

    companion object {
        val plainClient: OkHttpClient by lazy {
            OkHttpClient
                .Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()
        }

        fun client() : OkHttpClient {
            return plainClient
        }
    }

}
const val URL_API_WEATHER =
    "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=b80967f0a6bd10d23e44848547b26550&units=metric&lang=fr"
const val URL_API_RECIPES =
    "https://recipesapi2.p.rapidapi.com/recipes/%s?maxRecipes=2"

val client = ClientBuilder.client()
val gson = Gson()


class RequestUtils {

    companion object {
        fun loadWeather(city: String): WeatherBean {
            val json = sendGet(URL_API_WEATHER.format(city))
            return gson.fromJson(json, WeatherBean::class.java)
        }


        fun sendGet(url: String): String {
            println("url : $url")
            val request = Request.Builder().url(url).build()

            return client.newCall(request).execute().use {
                if (!it.isSuccessful) {
                    throw Exception("Réponse du serveur incorrect :${it.code}")
                }
                it.body?.string() ?: ""
            }
        }

        fun loadWeatherOpti(city: String): WeatherBean {
            val response = sendGetOpti(URL_API_WEATHER.format(city))
            val input = InputStreamReader(response.body?.byteStream())
            return gson.fromJson(input, WeatherBean::class.java)
        }

        fun loadRecipesApi(keyWord: String):RecipesDataBean{
            val response = sendGetOpti(URL_API_RECIPES.format(keyWord))
            val input = InputStreamReader(response.body?.byteStream())
            return gson.fromJson(input, RecipesDataBean::class.java)
        }

        fun loadRecipeApi(keyWord: String):RecipeBean{
            return loadRecipesApi(keyWord).data.get(0)
        }

        fun sendGetOpti(url: String): Response {
            println("url : $url")
            val request = Request.Builder()
                .url(url)
                .addHeader("X-RapidAPI-Key", "6d95189a52mshfe1aacf043c3b1fp162c2bjsnfc15fed28204")
                .build()
            val response = client.newCall(request).execute()

            return if (!response.isSuccessful) {
                response.close()
                throw Exception("Réponse du serveur incorrect : ${response.code}")
            } else {
                response
            }
        }
    }
}