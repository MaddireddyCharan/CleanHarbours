package com.p10.cleanharbours.ui.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.p10.cleanharbours.R
import com.p10.cleanharbours.databinding.ActivityCategoryBinding
import com.p10.cleanharbours.databinding.ActivityLoginBinding
import com.p10.cleanharbours.ui.pickup.PickUpActivity

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private val categoryViewModel : CategoryViewModel by viewModels()
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setHomeButtonEnabled(true);
        binding.categoriesList.layoutManager = GridLayoutManager(this, 2)

        binding.goBack.setOnClickListener {
            finish()
        }

        binding.proceed.setOnClickListener {
            val list = recyclerViewAdapter.categoryList.filter { it.isSelected }
            if(list.isNotEmpty()) {
                val intent = Intent(this, PickUpActivity::class.java)
                intent.putExtra(
                    "selectedCategories",
                    categoryViewModel.formCommaSeparatedString(list)
                )
                startActivity(intent)
            }
        }

        categoryViewModel.init(getSharedPreferences("MySharedPref", MODE_PRIVATE).getString("token", "").orEmpty())
        categoryViewModel.categoryList.observe(this, Observer {
            setCategoryList(it)
        })
    }

    private fun setCategoryList(wasteCategories: List<WasteCategory>?) {
        recyclerViewAdapter = RecyclerViewAdapter(wasteCategories as ArrayList<WasteCategory>, this)
        binding.categoriesList.adapter = recyclerViewAdapter
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