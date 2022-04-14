package com.matheusborba.projetkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.matheusborba.projetkotlin.databinding.ActivityWeatherBinding
import com.matheusborba.projetkotlin.model.WeatherViewModel
import com.squareup.picasso.Picasso


class WeatherActivity : AppCompatActivity(), View.OnClickListener {
    //IHM
    val binding by lazy { ActivityWeatherBinding.inflate(layoutInflater) }

    //Data
    val model by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btLoad.setOnClickListener(this)
        //Si l'activité a été recrée on a une donnée et on met à jour l'ihm


        //affichage des données
        model.data.observe(this) {
            //La nouvelle donnée s'appelle it
            binding.tvCity.text = it?.name ?: "-"
            binding.tvTemp.text = it?.temperature?.temp.toString() ?: "-"
            binding.tvMinMaxTemp.text =
                "(${it?.temperature?.temp_max.toString()}/ ${it?.temperature?.temp_max.toString()})"
                    ?: "-"
            binding.tvWind.text = it?.wind?.speed.toString() ?: "-"

            if(it != null && !it.weather.isNullOrEmpty()) {
                binding.tvDesc.text = it.weather[0].description
                Picasso.get().load("https://openweathermap.org/img/wn/${it.weather[0].icon}@4x.png").into(binding.ivTemp)
            }
            else {
                binding.tvDesc.text = "-"

            }
        }
        model.errorMessage.observe(this) {
            //La nouvelle donnée s'appelle it
            binding.tvError.isVisible = it != null
            binding.tvError.text = it ?: ""
        }
        model.threadRuning.observe(this) {
            //La nouvelle donnée s'appelle it
            binding.progressBar.isVisible = it
        }

    }


    override fun onClick(p0: View?) {
        binding.btLoad.setOnClickListener {
            //Recherche des données
            model.loadData()
        }

    }

}