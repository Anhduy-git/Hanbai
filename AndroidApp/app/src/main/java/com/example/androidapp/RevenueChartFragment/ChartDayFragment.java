package com.example.androidapp.RevenueChartFragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ChartDayFragment extends Fragment {
    //Variables
    private BarChart barChart;
    private TextView tvDailyRevenue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chart_day, container, false);
        initUI(v);

        setUpBarChart();
        loadBarChartData();
        onBarChartEvents();

        return v;
    }

    //Functions
    private void initUI(View v){
        barChart = v.findViewById(R.id.bar_chart_day);
        tvDailyRevenue = v.findViewById(R.id.day_revenue);
    }

    private void loadBarChartData(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        //test
        int n = 10;
        for (int i = 1; i < n; i++){
            entries.add(new BarEntry(2010 + i, 30*i));
        }

        BarDataSet barDataSet = new BarDataSet(entries, "");

        int[] colors = getContext().getResources().getIntArray(R.array.colors);
        barDataSet.setColor(colors[6]);

        BarData barData = new BarData(barDataSet);
        barData.setDrawValues(false);
        barData.setValueTextSize(16);
        barData.setValueTextColor(Color.BLACK);

        barDataSet.setDrawValues(true);
        barChart.setData(new BarData(barDataSet));
        barChart.invalidate();
    }

    private void setUpBarChart(){
        barChart.setFitBars(true);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(true);
        barChart.setDrawValueAboveBar(true);

    }

    private void onBarChartEvents(){
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                tvDailyRevenue.setText(String.valueOf(e.getY()));
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }
}