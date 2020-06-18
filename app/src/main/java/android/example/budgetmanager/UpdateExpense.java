package android.example.budgetmanager;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;

public class UpdateExpense extends AppCompatActivity implements AddCategory_custom.InsertCategoryListner {

    Spinner spinnerUpdateCategory;
    EditText updateAmount;
    String oldAmount="";
    String id,categoryChosen;
    String date;
    Button updateExpenseButton;
    Button cancelButton;
    DatabaseHelper myDb;
    Toolbar toolbar;
    Button addCategory;
    ArrayList<Category_List> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_expense);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();



        myDb = new DatabaseHelper(this);
        updateAmount = (EditText) findViewById(R.id.expenseToUpdate);

        categoryList = new ArrayList<>();

        spinnerUpdateCategory = (Spinner) findViewById(R.id.spinnerUpdateCategory);

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

         spinnerUpdateCategory.setAdapter(new Category_Adapter(this,categoryList));


        if(intent.hasExtra("budget_item_selected"))
        {
          BudgetItems budgetItemToUpdate = intent.getParcelableExtra("budget_item_selected");


            oldAmount = budgetItemToUpdate.getBudget_amount();
            this.id = budgetItemToUpdate.getBudget_id();
            this.date = budgetItemToUpdate.getBudget_date();




        }
        if(oldAmount != null)
        {

            updateAmount.setText(oldAmount);
        }

        updateExpenseButton = (Button) findViewById(R.id.updateExpenseButton);
        cancelButton = (Button) findViewById(R.id.cancelUpdateButton);

         UpdateBudgetData();
         NotToUpdate();

         addCategory = findViewById(R.id.addCategoryButton);
         addCategory.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 openDialog();
             }
         });

    }

    public void openDialog()
    {
        AddCategory_custom dialogBox = new AddCategory_custom();
        dialogBox.show(getSupportFragmentManager(),"Add Dialog");
    }



    public void UpdateBudgetData()
    {

        updateExpenseButton.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                if(updateAmount.getText().toString().trim().equalsIgnoreCase(""))
                {
                    updateAmount.setError("Enter a postive amount");
                }
                else {
                    String updatedAmount = updateAmount.getText().toString();
                    String updatedCategory = categoryChosen;

                    boolean isUpdate = myDb.updateBudgetData(id, date, updatedCategory, updatedAmount);

                    if (isUpdate == true) {
                        Toast.makeText(UpdateExpense.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UpdateExpense.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                }
            }

        });
    }

    public void NotToUpdate()
    {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateExpense.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void AddCategory(String newCategory) {
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
