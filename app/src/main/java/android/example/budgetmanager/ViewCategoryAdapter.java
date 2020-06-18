package android.example.budgetmanager;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewCategoryAdapter extends RecyclerView.Adapter<ViewCategoryAdapter.MyViewHolder> {

    private ArrayList<Category_List> categoryList;
    private OnCategoryItemListner mOnCategoryItemListner;

    public ViewCategoryAdapter(ArrayList<Category_List> categoryList, OnCategoryItemListner mOnCategoryItemListner) {
        this.categoryList = categoryList;
        this.mOnCategoryItemListner = mOnCategoryItemListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.display_category,parent,false);

        return new MyViewHolder(view,mOnCategoryItemListner) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category_List currentCategory = categoryList.get(position);
        holder.category_txt.setText(currentCategory.getCategory());


        if(position %5 == 1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#d8bfd8"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            if(position % 5 == 2)
            {
                holder.itemView.setBackgroundColor(Color.parseColor("#ffa07a"));
            }
            else
            {
                if(position % 5 == 3)
                {
                    holder.itemView.setBackgroundColor(Color.parseColor("#98fb98"));
                }
                else {
                    if(position % 5 == 4)
                    {
                        holder.itemView.setBackgroundColor(Color.parseColor("#ffe4c4"));
                    }
                    else
                    {
                        holder.itemView.setBackgroundColor(Color.parseColor("#deb887"));
                    }
                }
            }
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }


    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView category_txt;
        public CardView Category_Data;
        OnCategoryItemListner onCategoryItemListner;
        public MyViewHolder(@NonNull View itemView,OnCategoryItemListner onCategoryItemListner) {
            super(itemView);
            category_txt = itemView.findViewById(R.id.Category);
            Category_Data = itemView.findViewById(R.id.categoryData);

            this.onCategoryItemListner = onCategoryItemListner;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCategoryItemListner.OnCategoryItemClick(getAdapterPosition());

        }

        @Override
        public boolean onLongClick(View v) {
            onCategoryItemListner.OnCategoryItemLongClick(getAdapterPosition());
            return false;
        }
    }

    public interface OnCategoryItemListner
    {
        void OnCategoryItemClick(int position);

        void OnCategoryItemLongClick(int position);

    }
}
