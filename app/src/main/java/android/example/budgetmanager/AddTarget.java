package android.example.budgetmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

public class AddTarget extends AppCompatActivity {

    Toolbar toolbar;
    EditText targetAdded;
    Spinner spinnerMonth;
    Spinner spinnerYear;
    Button targetSet;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_target);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDb = new DatabaseHelper(this);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        targetAdded = (EditText) findViewById(R.id.targetAdded);
        spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Month,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapter);
        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.Year,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapter1);
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Intent intent = getIntent();
        targetSet = (Button) findViewById(R.id.addTargetButton);
        TargetAdded();

    }
    public void TargetAdded()
    {
        targetSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (targetAdded.getText().toString().trim().equalsIgnoreCase("")) {
                    targetAdded.setError("Enter a positive amount");

                } else {
                    final String year = spinnerYear.getSelectedItem().toString();
                    String month = "01";
                    final String d = "01";
                    switch (spinnerMonth.getSelectedItem().toString()) {
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
                    final String date = year + "-" + month + "-" + d;
                    final String amount = targetAdded.getText().toString();

                    Cursor cursor = myDb.getTargetData(date);

                    cursor.moveToFirst();

                    if (cursor.getCount() == 0) {
                        myDb.insertTargetData(date, "Target", amount);
                        Log.i("Uc", amount);
                        Log.i("Ud", date);
                        Toast.makeText(AddTarget.this, "Target successfully set for " + month + " " + year, Toast.LENGTH_SHORT).show();

                    } else {
                        final String finalMonth = month;
                        String a = cursor.getString(3);

                        new AlertDialog.Builder(AddTarget.this)
                                .setTitle("Target Already Set")
                                .setMessage("A target of Rs." + a + " has already been set for " + month + " " + year + ".Do you want to update the target?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        boolean result = myDb.updateTargetData(date, "Target", amount);

                                        if (result == true) {
                                            Log.i("Ua", amount);
                                            Log.i("Ub", date);
                                            Toast.makeText(AddTarget.this, "Target of Rs" + amount + " is successfully updated for " + finalMonth + " " + year, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(AddTarget.this, "Error occurred while updating target", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }


                }
            }
        });
    }
}
