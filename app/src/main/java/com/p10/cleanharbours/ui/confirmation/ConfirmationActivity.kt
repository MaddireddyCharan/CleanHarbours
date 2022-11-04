package com.p10.cleanharbours.ui.confirmation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.p10.cleanharbours.R
import com.p10.cleanharbours.databinding.ActivityConfirmationBinding
import com.p10.cleanharbours.ui.dashboard.DashboardActivity

class ConfirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfirmationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.pick_up)
        supportActionBar?.setHomeButtonEnabled(false)
        binding.visitDashboard.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }
}