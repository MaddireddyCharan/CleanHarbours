package com.p10.cleanharbours.ui.schedules_pickups

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.p10.cleanharbours.R
import com.p10.cleanharbours.databinding.ActivityScheduledPickUpBinding


class ScheduledPickUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScheduledPickUpBinding
    private val scheduledPickUpsViewModel : ScheduledPickUpsViewModel by viewModels()
    private lateinit var scheduledPickupsRecyclerViewAdapter: PickUpRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduledPickUpBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_scheduled_pick_up)
        supportActionBar?.title = getString(R.string.schedule_pickups)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setHomeButtonEnabled(true);
        scheduledPickUpsViewModel.init(
            getSharedPreferences("MySharedPref", MODE_PRIVATE).getString(
                "token",
                ""
            ).orEmpty())

        scheduledPickUpsViewModel.pickUpList.observe(this, Observer {
            setPickUpList(it)
        })
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

    private fun setPickUpList(pickUps: PickUps) {
        scheduledPickupsRecyclerViewAdapter = PickUpRecyclerViewAdapter(pickUps.pickups as ArrayList<PickupsItem>, this)
        binding.scheduledPickUps.adapter = scheduledPickupsRecyclerViewAdapter
    }
}