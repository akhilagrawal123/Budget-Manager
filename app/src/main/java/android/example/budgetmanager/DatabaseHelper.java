package android.example.budgetmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;



public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Budget_db";
    public static final String BUDGET = "BUDGET_TABLE";
    public static final String CATEGORY = "Category_Table";
    public static final String budget_id = "ID";
    public static final String category_id = "Category_ID";
    public static final String category_name = "Category_Name";
    public static final String budget_date = "BUDGET_DATE";
    public static final String budget_category = "Category";
    public static final String budget_amount = "BUDGET_AMOUNT";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ BUDGET +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, BUDGET_DATE TEXT, Category TEXT, BUDGET_AMOUNT INTEGER)");
        Log.i("DATABASE CREATED",BUDGET);
        db.execSQL("CREATE TABLE "+ CATEGORY +" (Category_ID INTEGER PRIMARY KEY AUTOINCREMENT, Category_Name TEXT)");

       String s[] = {"Books And Stationary", "Food", "Clothe Washing", "Recharge", "Party"};
       ContentValues values = new ContentValues();
       for(int i=0;i<s.length;i++)
       {
           values.put(category_name,s[i]);
           long res = db.insert(CATEGORY,category_id,values);
       }





    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BUDGET);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY);
        onCreate(db);

    }
    public boolean insertCategory(String new_category)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(category_name,new_category);
        long result = db.insert(CATEGORY,category_id,values);

        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public boolean insertBudgetData( String date, String category, String amount)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(budget_date,date);
        values.put(budget_category,category);
        values.put(budget_amount,amount);
        long result = db.insert(BUDGET,budget_id,values);

        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }
    public boolean insertTargetData(String date, String category, String amount)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(budget_date,date);
        values.put(budget_category,category);
        values.put(budget_amount,amount);
        long result = db.insert(BUDGET,budget_id,values);

        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Cursor getAllCategory()
    {
        String query = "SELECT * FROM " + CATEGORY;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        if(db!=null)
        {
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor getAllData()
    {
        String query = "SELECT * FROM " + BUDGET + " WHERE " + budget_category + " != ?";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        if(db != null)
        {
            String[] params = new String[]{ "Target" };
           cursor =  db.rawQuery(query,params);
        }
        return cursor;
    }
    public Cursor getDataForList(String date1, String date2)
    {
        String query = "SELECT * FROM " + BUDGET + " WHERE " + budget_date + " BETWEEN " + " ? " + " AND " + " ? AND "  + budget_category + " != ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("Date1",date1);
        Log.i("Date2",date2);

        Cursor cursor = null;
        if(db!=null)
        {
            String[] params = new String[] {date1,date2,"Target"};
            cursor = db.rawQuery(query,params);
        }

        return cursor;
    }
    public Cursor getDataForPie(String date1, String date2)
    {
        Log.i("Ua",date1);
        Log.i("Ub",date2);
        String query = "SELECT SUM(" + budget_amount + "), " + budget_category + " FROM " + BUDGET + " WHERE " +  budget_date + " BETWEEN " + " ? " + " AND " + " ? AND " + budget_category + " != ? GROUP BY " + budget_category;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        if(db != null)
        {
            String[] params = new String[] {date1,date2,"Target"};
            cursor = db.rawQuery(query,params);
        }
        return cursor;
    }
    public Cursor getTargetData(String date)
    {

        String query = "SELECT * FROM " + BUDGET + " WHERE " + budget_date + " = ?" + " AND " + budget_category + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        if(db!=null)
        {
            Log.i("S","s");
            String[] params = new String[]{ date , "Target" };
            cursor = db.rawQuery(query,params);
        }
        return cursor;
    }
    public Cursor getTarget()
    {
        String query = "SELECT * FROM " + BUDGET + " WHERE " + budget_category + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;

        if(db!=null)
        {
            String[] params = new String[]{ "Target" };
            cursor = db.rawQuery(query,params);
        }
        return cursor;
    }
    public Cursor getSum(String date1, String date2)
    {
        String query = "SELECT SUM(" + budget_amount + ") FROM " + BUDGET + " WHERE " + budget_date + " BETWEEN "+ " ? " + " AND " + " ? " +" AND " + budget_category + " <> ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db!=null){
            String[] params = new String[]{ date1, date2, "Target" };
            cursor = db.rawQuery(query,params);

        }
        return cursor;
    }
    public boolean updateBudgetData(String id, String date, String category, String amount)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(budget_id,id);
        values.put(budget_date,date);
        values.put(budget_category,category);
        values.put(budget_amount,amount);
        long result = db.update(BUDGET, values,"ID = ?",new String[] {id});
        Log.i("Update",String.valueOf(result));
        Log.i("Amount",amount);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }
    public boolean updateTargetData(String date, String category, String amount)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(budget_date,date);
        values.put(budget_category,category);
        values.put(budget_amount,amount);
        long result = db.update(BUDGET,values,budget_date + " = ?" + " AND " + budget_category + " = ?",new String[] { date , category });
        Log.i("UpdateT",String.valueOf(result));
        Log.i("UpdateTe",amount);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public boolean updateCategory(String updatecategory,String category)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(category_name,updatecategory);
        long res = db.update(CATEGORY,values, category_name + " = ?",new String[] {category});

        if(res == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }


    public Integer deleteData(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("Is","Done");
        return db.delete(BUDGET, "ID = ?",new String[] {id});
    }

    public Integer deleteCategory(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CATEGORY, "Category_Name = ?",new String[] {name});
    }



}
