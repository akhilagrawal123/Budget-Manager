package android.example.budgetmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ViewSetTarget extends AppCompatActivity implements TargetRecyclerAdapter.OnTargetItemListner, UpdateTarget_custom.UpdateTargetListner {

    Toolbar toolbar;
    RecyclerView recyclerView;
    TargetRecyclerAdapter adapter;
    ArrayList<BudgetItems> budgetItems;
    DatabaseHelper myDb;
    FloatingActionButton floatingActionButton;
    String date;
    String category;
    String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_set_target);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        Intent intent1 = getIntent();
        myDb = new DatabaseHelper(this);
        budgetItems = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewOfTarget);
        recyclerView.setHasFixedSize(true);
        adapter = new TargetRecyclerAdapter(budgetItems,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();

        floatingActionButton = (FloatingActionButton) findViewById(R.id.addTargetFloatingButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewSetTarget.this,AddTarget.class);
                startActivity(intent);
            }
        });

         storeTarget();

    }

    public void storeTarget()
    {
        Cursor cursor = myDb.getTarget();

        if(cursor.getCount() == 0)
        {
            Toast.makeText(this, "None target set yet!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while(cursor.moveToNext()) {
                budgetItems.add(new BudgetItems(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            }
        }
    }

    @Override
    public void OnTargetItemClick(int position) {

         date = budgetItems.get(position).getBudget_date();
         category = budgetItems.get(position).getBudget_category();
         amount = budgetItems.get(position).getBudget_amount();

        openDialog();

    }

    @Override
    public void OnTargetItemLongClick(final int position) {
        budgetItems.get(position);

        new AlertDialog.Builder(ViewSetTarget.this)
                .setTitle("Delete Target")
                .setMessage("Are you sure you want to delete \n this expense? You cannot undo this later")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Integer result = myDb.deleteData(budgetItems.get(position).getBudget_id());
                        if(result > 0)
                        {
                            Toast.makeText(ViewSetTarget.this, "Done", Toast.LENGTH_SHORT).show();
                            Log.i("Id",budgetItems.get(position).getBudget_id());
                        }
                        else
                        {
                            Toast.makeText(ViewSetTarget.this, "Not", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No",null)
                .show();



    }

    public void openDialog()
    {
        UpdateTarget_custom dialogBox = new UpdateTarget_custom(amount);
        dialogBox.show(getSupportFragmentManager(),"Update dialog");
    }

    @Override
    public void updateTarget(String updatedTarget) {

        boolean isUpdated = myDb.updateTargetData(date, category, updatedTarget);

        if(isUpdated == true)
        {
            Toast.makeText(this, "Target updated successfully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Some error occurred.Please try again after some time", Toast.LENGTH_SHORT).show();

        }

    }
}
