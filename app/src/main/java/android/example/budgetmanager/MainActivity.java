package android.example.budgetmanager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements RecyclerAdapter.OnBudgetItemListner {

    DatabaseHelper myHelper;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FloatingActionButton floatingActionButton;
    ArrayList<BudgetItems> budgetItems;
    RecyclerAdapter adapter;
    RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();

        Intent intent = getIntent();

        myHelper = new DatabaseHelper(this);
        budgetItems = new ArrayList<>();

         recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
         recyclerView.setHasFixedSize(true);
         adapter = new RecyclerAdapter(budgetItems,this);
         recyclerView.setAdapter(adapter);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));

         adapter.notifyDataSetChanged();




        floatingActionButton = (FloatingActionButton) findViewById(R.id.addExpenseButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddExpense.class);
                startActivity(intent);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.addTarget:
                        Intent intent = new Intent(MainActivity.this,AddTarget.class);
                        startActivity(intent);
                        break;
                    case R.id.viewSetTarget:
                        Intent intent1 = new Intent(MainActivity.this,ViewSetTarget.class);
                        startActivity(intent1);
                        break;
                    case R.id.viewAllCategories:
                         Intent intent2 = new Intent(MainActivity.this,ViewAllCategories.class);
                         startActivity(intent2);
                        break;
                    case R.id.viewMonthlyExpenses:
                        Intent intent3 = new Intent(MainActivity.this,ViewMonthlyExpenses.class);
                        startActivity(intent3);
                        break;
                }
                return false;
            }
        });


        storeDataInArrays();
    }

    public void storeDataInArrays()
    {
        Cursor cursor = myHelper.getAllData();

        cursor.moveToFirst();

        if(cursor.getCount() == 0)
        {
            Toast.makeText(this, "No data inserted yet!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            do
            {
                budgetItems.add(new BudgetItems(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }
            while (cursor.moveToNext());
        }
    }

    private  void setUpToolbar()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }


    @Override
    public void OnBudgetItemClick(int position) {
        budgetItems.get(position);

        Intent intent = new Intent(MainActivity.this,UpdateExpense.class);
        intent.putExtra("budget_item_selected",budgetItems.get(position));
        startActivity(intent);
    }

    @Override
    public void OnBudgetItemLongClick(final int position) {
        budgetItems.get(position);


        new AlertDialog.Builder(MainActivity.this)
                  .setTitle("Delete Expense")
                  .setMessage("Are you sure you want to delete \nthis expense? You cannot undo this later.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Integer result = myHelper.deleteData(budgetItems.get(position).getBudget_id());
                        if(result > 0)
                        {
                            Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                            Log.i("Id",budgetItems.get(position).getBudget_id());
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Not", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("No",null)
                .show();

    }
}
