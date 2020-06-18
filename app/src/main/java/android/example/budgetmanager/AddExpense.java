package android.example.budgetmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddExpense extends AppCompatActivity implements AddCategory_custom.InsertCategoryListner {

    Toolbar toolbar;
    DatabaseHelper myDb;
    Spinner spinnerCategory;
    EditText expenseAdded;
    ArrayList<Category_List> categoryList;
    Button addExpenseButton;
    Button addCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        myDb = new DatabaseHelper(this);

        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



        categoryList = new ArrayList<>();

        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);

        Cursor cursor = myDb.getAllCategory();
        cursor.moveToFirst();

        if(cursor.getCount() == 0)
        {
            Toast.makeText(this, "No category inserted yet!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            do
            {
                Category_List category_list = new Category_List();
                category_list.setCategory(cursor.getString(1));
                categoryList.add(category_list);
            }
            while(cursor.moveToNext());
        }

        spinnerCategory.setAdapter(new Category_Adapter(this,categoryList));





        Intent intent = getIntent();

        expenseAdded = (EditText) findViewById(R.id.expenseAdded);
        addExpenseButton = (Button) findViewById(R.id.addExpenseButton);
        addCategory = (Button) findViewById(R.id.newCategory);
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }
        });

        AddData();


    }

      public void openDialog()
        {
            AddCategory_custom dialogBox = new AddCategory_custom();
            dialogBox.show(getSupportFragmentManager(),"Add Dialog");
        }








    public String getDate()
    {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Log.i("date",date);

        return date;
    }

    public void AddData()
    {
        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(expenseAdded.getText().toString().trim().equalsIgnoreCase("")) {
                    expenseAdded.setError("Enter a positive amount");
                }
                else {

                    int year = Calendar.getInstance().get(Calendar.YEAR);
                    DateFormat dateFormat = new SimpleDateFormat("MM");
                    Date date = new Date();
                    String month = dateFormat.format(date);

                    String Date = String.valueOf(year) + "-" + month + "-" + "01";
                    String Date1 = String.valueOf(year) + "-" + month + "-" + "31";

                    Log.i("Date1", Date);
                    Log.i("Date2", Date1);

                    Cursor targetIsSet = myDb.getTargetData(Date);

                    targetIsSet.moveToFirst();


                    if (targetIsSet.getCount() == 0) {
                        Toast.makeText(AddExpense.this, "Firstly set the target for " + month + "," + year, Toast.LENGTH_SHORT).show();
                    } else {


                        Cursor getExpense = myDb.getSum(Date, Date1);

                        getExpense.moveToFirst();
                        int sum = getExpense.getInt(0);
                        int amountAdded = Integer.parseInt(expenseAdded.getText().toString());

                        int totalSum = sum + amountAdded;

                        int target = Integer.parseInt(targetIsSet.getString(3));

                        if (totalSum <= target) {

                            int amountLeft = target - totalSum;


                            boolean isInserted = myDb.insertBudgetData(getDate(), ((Category_List) spinnerCategory.getSelectedItem()).getCategory(), expenseAdded.getText().toString());

                            if (isInserted == true) {
                                Log.i("Date", getDate());
                                Log.i("Amount", expenseAdded.getText().toString());
                                Log.i("Category", ((Category_List) spinnerCategory.getSelectedItem()).getCategory());
                                Toast.makeText(AddExpense.this, "Data inserted. You can spend Rs." + amountLeft + " in this month", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddExpense.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            final int amountOverboard = totalSum - target;


                            new AlertDialog.Builder(AddExpense.this)
                                    .setTitle("Confirm Override")
                                    .setMessage("Adding this expense will cause your target to be overridden. Are you sure you want to continue")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            boolean isInserted = myDb.insertBudgetData(getDate(), ((Category_List) spinnerCategory.getSelectedItem()).getCategory(), expenseAdded.getText().toString());

                                            if (isInserted == true) {
                                                Log.i("Date", getDate());
                                                Log.i("Amount", expenseAdded.getText().toString());
                                                Log.i("Category", ((Category_List) spinnerCategory.getSelectedItem()).getCategory());
                                                Toast.makeText(AddExpense.this, "Data inserted. You have gone overboard by Rs." + amountOverboard + " in this month", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(AddExpense.this, "Error", Toast.LENGTH_SHORT).show();
                                            }


                                        }
                                    })
                                    .setNegativeButton("No", null)
                                    .show();


                        }
                    }
                }
                }


        });
    }

    @Override
    public void AddCategory(String newCategory)
    {

        boolean isInserted = myDb.insertCategory(newCategory);

        if(isInserted == true)
        {
            Toast.makeText(this, "Category added successfully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}
