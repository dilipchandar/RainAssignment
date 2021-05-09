package com.example.rainassignment

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.example.rainassignment.databinding.ActivityWithdrawalConfirmationBinding

class WithdrawalConfirmationActivity : Activity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWithdrawalConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE)

        val amount = sharedPreferences.getString("Amount", "")

        val confirmWithdrawalAmount = getString(R.string.confirm_withdrawal_amount) + amount

        binding.textConfirmwithdrawal.text = confirmWithdrawalAmount

        val arrivalConfirmation = getString(R.string.arrival_confirmation) + " " +
                sharedPreferences.getString("CardEndingNumber", "")
        binding.textArrivalconfirmation.text = arrivalConfirmation

        val buttonText = getString(R.string.withdraw) + amount
        binding.btnWithdrawal.text = buttonText

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

    }
}