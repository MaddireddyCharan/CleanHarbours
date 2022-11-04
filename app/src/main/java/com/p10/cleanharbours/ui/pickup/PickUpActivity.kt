package com.p10.cleanharbours.ui.pickup

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.p10.cleanharbours.R
import com.p10.cleanharbours.databinding.ActivityCategoryBinding
import com.p10.cleanharbours.databinding.ActivityPickUpBinding
import com.p10.cleanharbours.ui.category.CategoryViewModel
import com.p10.cleanharbours.ui.confirmation.ConfirmationActivity
import java.text.SimpleDateFormat
import java.util.*

class PickUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPickUpBinding
    private val pickUpViewModel : PickUpViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPickUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.pick_up)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setHomeButtonEnabled(true);
        val selectedCategories = intent.getStringExtra("selectedCategories").orEmpty()

        pickUpViewModel.init(
            getSharedPreferences("MySharedPref", MODE_PRIVATE).getString(
                "token",
                ""
            ).orEmpty()
        )

        binding.schedule.setOnClickListener {
            pickUpViewModel.formDataModel(
                selectedCategories,
                binding.city.selectedItem.toString(),
                binding.street.text.toString(),
                binding.pincode.text.toString(),
                binding.otherInfo.text.toString()
            )
        }

        pickUpViewModel.pickUpsLiveData.observe(this, Observer {
            startActivity(Intent(this, ConfirmationActivity::class.java))
        })

        binding.expectedPickUp.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())

        val cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            binding.expectedPickUp.text = sdf.format(cal.time)
        }

        binding.expectedPickUp.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}