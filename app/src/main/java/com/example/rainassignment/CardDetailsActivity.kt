package com.example.rainassignment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.rainassignment.databinding.ActivityCardDetailsBinding
import java.text.SimpleDateFormat
import java.util.*

class CardDetailsActivity : Activity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCardDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE)

        binding.btnContinue.setOnClickListener {
            if (binding.editCardnumber.text.isEmpty()) {
                binding.editCardnumber.error = getString(R.string.card_error)
            } else if (binding.editExpirationdate.text.isEmpty()) {
                binding.editExpirationdate.error = getString(R.string.date_error)
            } else if (binding.editCvv.text.isEmpty()) {
                binding.editCvv.error = getString(R.string.cvv_error)
            } else {
                if (binding.editCardnumber.text.length < 16) {
                    binding.editCardnumber.error = getString(R.string.invalid_card)
                } else if (!isValidDate(binding.editExpirationdate.text.toString())) {
                    binding.editExpirationdate.error = getString(R.string.invalid_expiration_date)
                } else if (binding.editCvv.text.length < 3) {
                    binding.editCvv.error = getString(R.string.invalid_cvv)
                } else {
                    val cardEndingNumber = binding.editCardnumber.text.substring(
                        12,
                        binding.editCardnumber.text.length
                    )
                    val editor = sharedPreferences.edit()
                    editor.putString("CardEndingNumber", cardEndingNumber)
                    editor.apply()
                    startActivity(Intent(this, WithdrawalConfirmationActivity::class.java))
                }
            }
        }

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        binding.editExpirationdate.addTextChangedListener(object : TextWatcher {

            var sb: StringBuilder = StringBuilder("")

            var _ignore = false

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (_ignore) {
                    _ignore = false
                    return
                }

                sb.clear()
                sb.append(
                    if (s!!.length > 10) {
                        s.subSequence(0, 10)
                    } else {
                        s
                    }
                )

                if (sb.lastIndex == 2) {
                    if (sb[2] != '/') {
                        sb.insert(2, "/")
                    }
                } else if (sb.lastIndex == 5) {
                    if (sb[5] != '/') {
                        sb.insert(5, "/")
                    }
                }

                _ignore = true
                binding.editExpirationdate.setText(sb.toString())
                binding.editExpirationdate.setSelection(sb.length)

            }
        })

    }


    private fun isValidDate(pDateString: String?): Boolean {
        val date: Date = SimpleDateFormat("dd/MM/yy").parse(pDateString)
        return Date().before(date)
    }
}