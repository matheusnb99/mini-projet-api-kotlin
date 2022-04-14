package com.matheusborba.projetkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.matheusborba.projetkotlin.R.layout
import com.matheusborba.projetkotlin.databinding.ActivityRecipesBinding
import com.matheusborba.projetkotlin.model.RecipesViewModel
import com.matheusborba.projetkotlin.model.WeatherViewModel
import com.squareup.picasso.Picasso

class RecipesActivity : AppCompatActivity(), View.OnClickListener {
    val binding by lazy {ActivityRecipesBinding.inflate(layoutInflater)}
    //Data
    val model by lazy { ViewModelProvider(this).get(RecipesViewModel::class.java) }


    fun listToSingleLine(list:List<String>):String{
        var line:String = ""
        for(element in list){
            line += "${element}\n"
        }
        return line
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btLoad.setOnClickListener(this)
        model.loadData()

        //affichage des données
        model.data.observe(this) {
            println(it)
            binding.tvTitle.text = it?.name ?: "-"
            Picasso.get().load(it?.image).into(binding.ivFood)


            binding.tvIngredients.text = it?.ingredients?.let { it1 -> listToSingleLine(it1) } ?: "-"
            binding.tvInstructions.text = it?.instructions?.let { it1 -> listToSingleLine(it1) } ?: "-"
            binding.tvServes.text = "${it?.servings} portions" ?: "at least one I guess"
            binding.tvTime.text = "preparation time: ${it?.time?.prepration_time}\ncooking time: ${it?.time?.cooking_time}\ntotal time: ${it?.time?.total}" ?: "Unknown"
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

