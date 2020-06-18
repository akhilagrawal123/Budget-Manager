package android.example.budgetmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.anychart.charts.Pie;

public class ViewMonthlyExpenses extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton list,bar,pie;
    Toolbar toolbar;
    DatabaseHelper myDb;
    String date1,date2;
    Spinner spinnerMonth;
    Spinner spinnerYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_monthly_expenses);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDb = new DatabaseHelper(this);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        spinnerMonth = (Spinner) findViewById(R.id.spinnerMonthView);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.Month,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(arrayAdapter);
        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerYear = (Spinner) findViewById(R.id.spinnerYearView);
        ArrayAdapter<CharSequence> arrayAdapter1 = ArrayAdapter.createFromResource(this,R.array.Year,android.R.layout.simple_spinner_item);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(arrayAdapter1);
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        final Intent intent = getIntent();


        radioGroup = findViewById(R.id.radioGroup);
        list = findViewById(R.id.list);
        bar = findViewById(R.id.bar);
        pie = findViewById(R.id.pie);

        Button viewButton = (Button) findViewById(R.id.viewButton);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("Date3",spinnerMonth.getSelectedItem().toString());
                Log.i("Date4",spinnerYear.getSelectedItem().toString());

                String month = "01";
                String m = spinnerMonth.getSelectedItem().toString();


                switch (spinnerMonth.getSelectedItem().toString())
                {
                    case "January":
                        month = "01";
                        break;
                    case "February":
                        month = "02";
                        break;
                    case "March":
                        month = "03";
                        break;
                    case "April":
                        month = "04";
                        break;
                    case "May":
                        month = "05";
                        break;
                    case "June":
                        month = "06";
                        break;
                    case "July":
                        month = "07";
                        break;
                    case "August":
                        month = "08";
                        break;
                    case "September":
                        month = "09";
                        break;
                    case "October":
                        month = "10";
                        break;
                    case "November":
                        month = "11";
                        break;
                    case "December":
                        month = "12";
                        break;

                }


               String year  = spinnerYear.getSelectedItem().toString();
                date1 = year + "-" + month + "-" + "01";
                date2 = year + "-" + month + "-" + "31";

                if(list.isChecked())
                {
                    Intent intent1 = new Intent(ViewMonthlyExpenses.this,ListOfExpenses.class);
                    intent1.putExtra("DateStart",date1);
                    intent1.putExtra("DateEnd",date2);
                    startActivity(intent1);
                }
                else
                {
                    if(bar.isChecked())
                    {
                        Intent intent1 = new Intent(ViewMonthlyExpenses.this, BarChart.class);
                        intent1.putExtra("DateStart",date1);
                        intent1.putExtra("DateEnd",date2);
                        intent1.putExtra("Month",m);
                        intent1.putExtra("Year",year);
                        startActivity(intent1);

                    }
                    else
                    {
                        if(pie.isChecked())
                        {

                            Intent intent1 = new Intent(ViewMonthlyExpenses.this, PieChart.class);
                            intent1.putExtra("DateStart",date1);
                            intent1.putExtra("DateEnd",date2);
                            intent1.putExtra("Month",m);
                            intent1.putExtra("Year",year);
                            startActivity(intent1);
                        }
                    }
                }

            }
        });
    }


}
