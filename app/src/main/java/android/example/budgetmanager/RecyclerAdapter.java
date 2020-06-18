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

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>  {



    private ArrayList<BudgetItems> BudgetItemsList;
    private OnBudgetItemListner mOnBudgetItemListner;

    public RecyclerAdapter(ArrayList<BudgetItems> budgetItemsList, OnBudgetItemListner onBudgetItemListner) {
        this.BudgetItemsList = budgetItemsList;
        this.mOnBudgetItemListner = onBudgetItemListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.display_data,parent,false);

        return new MyViewHolder(view,mOnBudgetItemListner) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BudgetItems currentItem = BudgetItemsList.get(position);
        holder.amount_txt.setText(currentItem.getBudget_amount());
        holder.category_txt.setText(currentItem.getBudget_category());
        String date = DateConvertor(currentItem.getBudget_date(),true);
        holder.date_txt.setText(date);



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
        return BudgetItemsList.size();
    }



    public static  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView amount_txt;
        public TextView category_txt;
        public TextView date_txt;
        public CardView expenseAdded;
        OnBudgetItemListner onBudgetItemListner;
        public MyViewHolder(@NonNull View itemView, OnBudgetItemListner onBudgetItemListner)  {
            super(itemView);
            amount_txt = itemView.findViewById(R.id.amount);
            category_txt = itemView.findViewById(R.id.category);
            date_txt = itemView.findViewById(R.id.date);
            expenseAdded = itemView.findViewById(R.id.expenseAdded);
            this.onBudgetItemListner = onBudgetItemListner;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onBudgetItemListner.OnBudgetItemClick(getAdapterPosition());

        }

        @Override
        public boolean onLongClick(View v) {
            onBudgetItemListner.OnBudgetItemLongClick(getAdapterPosition());
            return false;
        }

    }
    public interface OnBudgetItemListner
    {
        void OnBudgetItemClick(int position);

        void OnBudgetItemLongClick(int position);
    }

    public static String DateConvertor(String date, boolean x)
    {
        String[] arrOfStr = date.split("-",3);

        String d,month,year,monthChr;

        d = arrOfStr[2];
        month = arrOfStr[1];
        year = arrOfStr[0];

        switch (month)
        {
            case "01":
                monthChr = "January";
                break;
            case "02":
                monthChr = "February";
                break;
            case "03":
                monthChr = "March";
                break;
            case "04":
                monthChr = "April";
                break;
            case "05":
                monthChr = "May";
                break;
            case "06":
                monthChr = "June";
                break;
            case "07":
                monthChr = "July";
                break;
            case "08":
                monthChr = "August";
                break;
            case "09":
                monthChr = "September";
                break;
            case "10":
                monthChr = "October";
                break;
            case "11":
                monthChr = "November";
                break;
            case "12":
                monthChr = "December";
                break;
            default:
                monthChr = "Error";
                break;
        }

        String newDate = d + " " + monthChr + " " + year;
        String newTargetDate = monthChr + " " + year;

        if(x==true){
            return newDate;
        }
        else
        {
            return newTargetDate;
        }





    }
}
