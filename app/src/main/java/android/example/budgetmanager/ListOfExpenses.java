package android.example.budgetmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class ListOfExpenses extends AppCompatActivity implements RecyclerAdapter.OnBudgetItemListner {

    String dateStart,dateEnd;
    DatabaseHelper myDb;
    ArrayList<BudgetItems> budgetItems;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_expenses);

        Intent intent = getIntent();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDb = new DatabaseHelper(this);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        dateStart = intent.getStringExtra("DateStart");
        dateEnd = intent.getStringExtra("DateEnd");

        myDb = new DatabaseHelper(this);
        budgetItems = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewList);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerAdapter(budgetItems,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
        storeDataInList();

    }

    public void storeDataInList()
    {
        Cursor cursor = myDb.getDataForList(dateStart,dateEnd);

        cursor.moveToFirst();

        if(cursor.getCount() == 0)
        {
            Toast.makeText(this, "Nothing to show for this month", Toast.LENGTH_SHORT).show();
        }
        else
        {
            do{
                budgetItems.add(new BudgetItems(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }
            while (cursor.moveToNext());
        }
    }

    @Override
    public void OnBudgetItemClick(int position) {

    }

    @Override
    public void OnBudgetItemLongClick(int position) {

    }
}
