package com.example.androidapp.RevenueChartFragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidapp.Data.AppDatabase;
import com.example.androidapp.Data.MonthRevenueData.MonthRevenue;
import com.example.androidapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChartMonthFragment extends Fragment {
    private BarChart barChart;
    private TextView tvMonthlyRevenue;

    private Date nowDate;
    private DateFormat formatter;
    private String strDate;

    private ArrayList<BarEntry> entries = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chart_month, container, false);

        //Date related
        nowDate = Calendar.getInstance().getTime();
        formatter = new SimpleDateFormat("MM/yyyy");
        strDate = formatter.format(nowDate);

        initUI(v);

        setUpBarChart();
        loadBarChartData();
        onBarChartEvents();

        return v;
    }

    private void initUI(View v){
        barChart = v.findViewById(R.id.bar_chart_month);
        tvMonthlyRevenue = v.findViewById(R.id.month_revenue);
    }

    private void setUpBarChart(){
        barChart.setFitBars(true);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(true);
        barChart.setDrawValueAboveBar(true);
    }

    private void loadBarChartData(){
        //test
        List<MonthRevenue> monthRevenueList = AppDatabase.getInstance(getActivity()).monthRevenueDao().getAllMonthRevenues();
        for (MonthRevenue monthRevenue : monthRevenueList) {
            int month = Integer.valueOf(monthRevenue.getCurrentDate().substring(0, 2));
            entries.add(new BarEntry(month, (float) monthRevenue.getMonthRevenue()));
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

        barChart.notifyDataSetChanged();
        barChart.invalidate();
    }

    private void onBarChartEvents(){
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                tvMonthlyRevenue.setText(String.valueOf(e.getY()));
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

//    private MonthRevenue getMonth(List<MonthRevenue> monthRevenueList, String currentDate) {
//        MonthRevenue temp = new MonthRevenue("", 0, 0);
//        for (MonthRevenue monthRevenue : monthRevenueList) {
//            if (monthRevenue.getCurrentDate().equals(currentDate)) {
//                temp = monthRevenue;
//            }
//        }
//
//        return temp;
//    }
}