package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.API.APIService;
import com.example.myapplication.API.ChartService;
import com.example.myapplication.Model.itemBar;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartActivity extends AppCompatActivity {
    ArrayList<itemBar>list_barChart=new ArrayList<itemBar>();
    ArrayList<itemBar>list_pieChart=new ArrayList<itemBar>();
    PieChart pieChart;
    BarChart barChart;
    ImageView imageView_Return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        Init();
        setUpPieChart();
        setUpBarChart();
        ReturnDashBoard();
    }
    public void Init()
    {
        pieChart=findViewById(R.id.pieChart);
        barChart=findViewById(R.id.barChart);
        imageView_Return=findViewById(R.id.imageView_Return);
    }

    public void ReturnDashBoard()
    {
        imageView_Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void setPieChart(){
        ArrayList<PieEntry>list_dataPie=new ArrayList<PieEntry>();
        for(itemBar i:list_pieChart)
        {
            list_dataPie.add(new PieEntry(i.amount,i.ItemName));
        }
        PieDataSet pieDataSet=new PieDataSet(list_dataPie,"list_category");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieChart.getDescription().setEnabled(false);
        pieChart.setData(new PieData(pieDataSet));
    }
    public void setUpPieChart()
    {
        ChartService chartService= APIService.retrofit.create(ChartService.class);
        Call<ArrayList<itemBar>>call=chartService.getquantityCategory();
        call.enqueue(new Callback<ArrayList<itemBar>>() {
            @Override
            public void onResponse(Call<ArrayList<itemBar>> call, Response<ArrayList<itemBar>> response) {
                list_pieChart=response.body();
                setPieChart();
                Log.e("list", "chart: "+list_pieChart.toString());
            }
            @Override
            public void onFailure(Call<ArrayList<itemBar>> call, Throwable t) {
                Log.e("list", "chart: "+t.getMessage());
            }
        });
    }
    public void setBarChart(){
        barChart.getAxisRight().setDrawLabels(false);
        ArrayList<BarEntry>list_dataBar=new ArrayList<BarEntry>();
        List<String> xValue=new ArrayList<String>();
        for(int i=0;i<list_barChart.size();i++)
        {
            list_dataBar.add(new BarEntry(i,list_barChart.get(i).amount));
            xValue.add(list_barChart.get(i).ItemName);
        }
        YAxis yAxis=barChart.getAxisLeft();
        yAxis.setAxisMaximum(0f);
        yAxis.setAxisMaximum(this.maxAmount());
        yAxis.setZeroLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);
        BarDataSet barDataSet=new BarDataSet(list_dataBar,"");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barChart.setData(new BarData(barDataSet));
        barChart.getDescription().setEnabled(false);
        barChart.invalidate();
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValue));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setGranularityEnabled(true);
    }
    public void setUpBarChart()
    {
        ChartService chartService= APIService.retrofit.create(ChartService.class);
        Call<ArrayList<itemBar>>call=chartService.getquantityBrand();
        call.enqueue(new Callback<ArrayList<itemBar>>() {
            @Override
            public void onResponse(Call<ArrayList<itemBar>> call, Response<ArrayList<itemBar>> response) {
                list_barChart=response.body();
                setBarChart();
            }
            @Override
            public void onFailure(Call<ArrayList<itemBar>> call, Throwable t) {
                Log.e("list", "chart: "+t.getMessage());
            }
        });
    }
    public int maxAmount()
    {
        int s=0;
        for(itemBar i:list_barChart){
            if(i.amount>s)
            {
                s=i.amount;
            }
        }
        return s;
    }
}