package android.example.budgetmanager;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Category_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Category_List> categoryList;

    public Category_Adapter(Context context, ArrayList<Category_List> categoryList) {
        super();
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Category_List current_List = categoryList.get(position);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.row,parent,false);
        TextView category = (TextView) row.findViewById(R.id.category_row);
        category.setText(current_List.getCategory());

        return row;
    }
}
