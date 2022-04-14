package com.matheusborba.projetkotlin

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.matheusborba.projetkotlin.databinding.ActivityMainBinding

const val MENU_ID_TP = 5
const val MENU_ID_DP = 6
const val MENU_ID_WEATHER = 7
const val MENU_ID_RECIPES = 8

class MainActivity : AppCompatActivity(), View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    //IHM
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btValidate.setOnClickListener(this)
        binding.btCancel.setOnClickListener(this)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0, MENU_ID_TP, 0, "TimePicker")
        menu?.add(0, MENU_ID_DP, 0, "DatePicker")
        menu?.add(0, MENU_ID_WEATHER, 0, "Météo")
        menu?.add(0, MENU_ID_RECIPES, 0, "Oui Chef")

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == MENU_ID_TP) {
            TimePickerDialog(this, this, 10, 22, true).show()
        } else if (item.itemId == MENU_ID_DP) {
            DatePickerDialog(this, this, 2022, 10, 25).show()
        } else if (item.itemId == MENU_ID_WEATHER) {
            val intent = Intent(this, WeatherActivity::class.java)
            startActivity(intent)
        } else if (item.itemId == MENU_ID_RECIPES) {
            val intent = Intent(this, RecipesActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        if (binding.btValidate === v) {
            if (binding.rbLike.isChecked) {
                binding.et.setText(binding.rbLike.text)
            } else if (binding.rbDislike.isChecked) {
                binding.et.setText(binding.rbDislike.text)
            }
            binding.iv.setImageResource(R.drawable.ic_baseline_flag_24)
            binding.iv.setColorFilter(Color.GREEN)

        } else if (binding.btCancel === v) {
            binding.et.setText("")
            //Vide les radiogroupes
            binding.rg.clearCheck()
            binding.iv.setImageResource(R.drawable.ic_baseline_delete_forever_24)
        }

    }

    //Callback du TimePicker
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Toast.makeText(this, "$hourOfDay:$minute", Toast.LENGTH_LONG).show()
    }

    //Callback du DatePicker
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Toast.makeText(this, "$dayOfMonth/$month/$year", Toast.LENGTH_LONG).show()
    }
}


