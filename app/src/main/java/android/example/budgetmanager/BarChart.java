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
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;

import java.util.ArrayList;
import java.util.List;

public class BarChart extends AppCompatActivity {

    AnyChartView BarChart;
    Toolbar toolbar;
    String dateStart,dateEnd,month,year;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

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

        BarChart = findViewById(R.id.barChart);


        setBarChart();


    }

    public void setBarChart()
    {
        BarChart.setProgressBar(findViewById(R.id.progressBar));

        Cartesian cartesian = AnyChart.column();

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

        Column column = cartesian.column(dataEntries);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Monthly expenditure in " + month + " " + year);

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("Rs.{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Categories");
        cartesian.yAxis(0).title("Money");

        BarChart.setChart(cartesian);


    }
}
