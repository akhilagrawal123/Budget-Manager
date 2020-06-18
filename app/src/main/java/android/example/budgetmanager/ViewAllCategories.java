package android.example.budgetmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ViewAllCategories extends AppCompatActivity implements ViewCategoryAdapter.OnCategoryItemListner,AddCategory_custom.InsertCategoryListner, updateCategory_custom.UpdateCategoryListner {

    Toolbar toolbar;
    DatabaseHelper myDb;
    ArrayList<Category_List> categoryLists;
    ViewCategoryAdapter CategoryAdapter;
    RecyclerView recyclerView;
    String oldCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_categories);
        Intent intent = getIntent();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        myDb = new DatabaseHelper(this);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        categoryLists = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewCategory);
        recyclerView.setHasFixedSize(true);
        CategoryAdapter = new ViewCategoryAdapter(categoryLists,this);
        recyclerView.setAdapter(CategoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CategoryAdapter.notifyDataSetChanged();

        FloatingActionButton addCategoryFloatingButton = findViewById(R.id.addNewCategoryButton);
        addCategoryFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialog();
            }
        });

        ShowCategory();


    }
    public void ShowCategory()
    {
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
                categoryLists.add(category_list);
            }
            while(cursor.moveToNext());
        }
    }

    public void openDialog()
    {
        AddCategory_custom dialogBox = new AddCategory_custom();
        dialogBox.show(getSupportFragmentManager(),"Add Dialog");
    }


    @Override
    public void OnCategoryItemClick(int position) {

       oldCategory = categoryLists.get(position).getCategory();
       OpenDialogAgain();

    }

    @Override
    public void OnCategoryItemLongClick(final int position) {
        categoryLists.get(position);



        new AlertDialog.Builder(ViewAllCategories.this)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete ? You cannot undo this later.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Integer result = myDb.deleteCategory(categoryLists.get(position).getCategory());
                        if(result > 0)
                        {
                            Toast.makeText(ViewAllCategories.this, "Done", Toast.LENGTH_SHORT).show();
                            Log.i("Id",categoryLists.get(position).getCategory());
                        }
                        else
                        {
                            Toast.makeText(ViewAllCategories.this, "Not", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No",null)
                .show();

    }

    public void OpenDialogAgain()
    {
        updateCategory_custom dialogBox = new updateCategory_custom(oldCategory);
        dialogBox.show(getSupportFragmentManager(),"Add Dialog");
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

    @Override
    public void UpdateCategory(String category) {
        boolean isUpdated = myDb.updateCategory(category, oldCategory);

        if(isUpdated == true)
        {
            Toast.makeText(this, "Category updated successfully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}
