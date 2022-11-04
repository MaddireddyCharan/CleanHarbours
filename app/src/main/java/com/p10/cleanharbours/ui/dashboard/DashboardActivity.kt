package com.p10.cleanharbours.ui.dashboard
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.google.android.material.button.MaterialButton
import com.p10.cleanharbours.R
import com.p10.cleanharbours.ui.category.CategoryActivity
import com.p10.cleanharbours.ui.login.LoginViewModel
import com.p10.cleanharbours.ui.login.LoginViewModelFactory
import com.p10.cleanharbours.ui.schedules_pickups.ScheduledPickUpActivity


class DashboardActivity : AppCompatActivity() {

    lateinit var chart : PieChart
    lateinit var pickUp : MaterialButton
    lateinit var scheduledPickUps : MaterialButton

    private val dashBoardViewModel: DashBoardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        dashBoardViewModel.init(getSharedPreferences("MySharedPref", MODE_PRIVATE).getString("token", "").orEmpty())
        chart = findViewById(R.id.chart1)
        pickUp = findViewById(R.id.pickUP)
        pickUp.setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java))
        }
        scheduledPickUps = findViewById(R.id.scheduledPickUps)
        scheduledPickUps.setOnClickListener {
            startActivity(Intent(this, ScheduledPickUpActivity::class.java))
        }

        chart.setUsePercentValues(true);
        chart.description.isEnabled = false;
        chart.setExtraOffsets(5F, 10F, 5F, 5F);

        chart.dragDecelerationFrictionCoef = 0.95f;

        //chart.setCenterTextTypeface(tfLight);
        chart.centerText = "Clean Harbours";

        chart.isDrawHoleEnabled = true;
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.holeRadius = 58f;
        chart.transparentCircleRadius = 61f;

        chart.setDrawCenterText(true);

        chart.rotationAngle = 0F;
        // enable rotation of the chart by touch
        chart.isRotationEnabled = true;
        chart.isHighlightPerTapEnabled = true;

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        //chart.setOnChartValueSelectedListener(this);
        chart.animateY(1400, Easing.EaseInOutQuad)
        // chart.spin(2000, 0, 360);

        // chart.spin(2000, 0, 360);
        val l = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f

        // entry label styling

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE)
        //chart.setEntryLabelTypeface(tfRegular)
        chart.setEntryLabelTextSize(12f)

        dashBoardViewModel.dashboardResponse.observe(this, Observer {
            setData(it)
        })

    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_items, menu)
        return true
    }

    private fun setData(dashBoardResponse: DashboardResponseModel) {
        val entries: ArrayList<PieEntry> = ArrayList()
        var count = 1
        if(dashBoardResponse.wasteData != null){
            count = dashBoardResponse.wasteData.size
        }
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (i in 0 until count) {
            var entry = 1f
            if(dashBoardResponse.wasteData?.get(i)?.itemsPickedUp != null) {
                entry = dashBoardResponse.wasteData[i]?.itemsPickedUp?.toFloat()!!
                entries.add(PieEntry(entry, dashBoardResponse.wasteData[i]?.type))
            }
        }
        val dataSet = PieDataSet(entries, "Waste Dashboard")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0F, 40F)
        dataSet.selectionShift = 5f

        // add a lot of colors
        val colors: ArrayList<Int> = ArrayList()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)
        colors.add(ColorTemplate.getHoloBlue())
        dataSet.colors = colors
        //dataSet.setSelectionShift(0f);
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(20f)
        data.setValueTextColor(Color.BLACK)
        //data.setValueTypeface(tfLight)
        chart.data = data

        // undo all highlights
        chart.highlightValues(null)
        chart.invalidate()
    }

}