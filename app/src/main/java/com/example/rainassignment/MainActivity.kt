package com.example.rainassignment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rainassignment.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    var MIN = 0
    var MAX = 41
    var STEP = 10
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE)

        binding.seekbar.max = ((MAX - MIN) / STEP)
        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                val progressCustom = MIN + (progress * STEP)
                binding.textWithdrawamount.text = progressCustom.toString()

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar?.progress == seekBar?.max) {
                    val newMax = MAX
                    binding.textWithdrawamount.text = newMax.toString()
                }
            }
        })


        binding.btnContinue.setOnClickListener {

            if (binding.seekbar.progress > 0) {
                val editor = sharedPreferences.edit();
                editor.putString("Amount", binding.textWithdrawamount.text.toString())
                editor.apply()
                startActivity(Intent(this, CardDetailsActivity::class.java))
            } else {
                Toast.makeText(this, "Select some amount to withdraw", Toast.LENGTH_SHORT).show()
            }

        }

    }
}