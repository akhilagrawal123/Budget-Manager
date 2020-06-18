package android.example.budgetmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

public class PieChart extends AppCompatActivity {

    AnyChartView pieChart;
    Toolbar toolbar;
    String dateStart,dateEnd,month,year;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

       Intent intent = getIntent();
        dateStart = intent.getStringExtra("DateStart");
        dateEnd = intent.getStringExtra("DateEnd");
        month = intent.getStringExtra("Month");
        year = intent.getStringExtra("Year");

        myDb = new DatabaseHelper(this);


        pieChart = findViewById(R.id.pieChart);
        TextView pieText = findViewById(R.id.textPie);
        pieText.setText("Monthly Expenditure in" + month + " " + year);

        setupPieChart();



    }
    public void setupPieChart()
    {
        Pie pie = AnyChart.pie();

        List<DataEntry> dataEntries = new ArrayList<>();

        Cursor cursor = myDb.getDataForPie(dateStart,dateEnd);

        cursor.moveToFirst();

        if(cursor.getCount() == 0)
        {
            Toast.makeText(this, "No any expenses inserted for this month", Toast.LENGTH_SHORT).show();
        }
        else
        {
            do {
                Log.i("Uc",cursor.getString(1));
                Log.i("Ud",String.valueOf(cursor.getInt(0)));

                dataEntries.add(new ValueDataEntry(cursor.getString(1),cursor.getInt(0)));
            }
            while (cursor.moveToNext());
        }





        pie.data(dataEntries);
        pieChart.setChart(pie);
    }
}
