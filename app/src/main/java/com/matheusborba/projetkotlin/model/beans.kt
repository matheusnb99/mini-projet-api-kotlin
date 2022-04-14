package com.matheusborba.projetkotlin.model

import com.google.gson.annotations.SerializedName

data class WeatherBean(
    val coord: Coord,
    @SerializedName("main")
    val temperature: Temperature,
    val name: String,
    val weather: List<Weather>,
    val wind: Wind
)

data class Coord(
    val lat: Double,
    val lon: Double
)

data class Temperature(
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)

data class Weather(
    val description: String,
    val icon: String,
    val main: String
)

data class Wind(
    val deg: Int,
    val speed: Double
)
data class RecipesDataBean(
    val `data`: List<RecipeBean>
)


data class RecipeBean(
    val image: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val name: String,
    val nutrients: NutrientsBean,
    val servings: String,
    val tags: List<Any>,
    val time: TimeBean
)

data class NutrientsBean(
    val calcium: String,
    @SerializedName("calories from fat")
    val caloriesFromFat: String,
    val carbohydrates: String,
    val cholesterol: String,
    val fat: String,
    val folate: String,
    val iron: String,
    val magnesium: String,
    val potassium: String,
    val protein: String,
    @SerializedName("saturated fat")
    val saturatedFat: String,
    val sodium: String,
    val sugars: String,
    val thiamin: String,
    @SerializedName("vitamin a iu")
    val vitaminAIu: String,
    @SerializedName("vitamin b6")
    val vitaminB6: String,
    @SerializedName("vitamin c")
    val vitaminC: String
)

data class TimeBean(
    val additional_time: String,
    val cooking_time: String,
    val prepration_time: String,
    val total: String
)