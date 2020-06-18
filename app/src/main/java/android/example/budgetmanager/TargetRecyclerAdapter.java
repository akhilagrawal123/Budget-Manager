package android.example.budgetmanager;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TargetRecyclerAdapter extends RecyclerView.Adapter<TargetRecyclerAdapter.TargetViewHolder> {

    private ArrayList<BudgetItems> BudgetItemsList;
    private OnTargetItemListner mOnTargetItemListner;

    public TargetRecyclerAdapter(ArrayList<BudgetItems> budgetItemsList, OnTargetItemListner onTargetItemListner) {
        this.BudgetItemsList = budgetItemsList;
        this.mOnTargetItemListner = onTargetItemListner;
    }

    @NonNull
    @Override
    public TargetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.display_target,parent,false);

        return new TargetViewHolder(view,mOnTargetItemListner);
    }

    @Override
    public void onBindViewHolder(@NonNull TargetViewHolder holder, int position) {
        BudgetItems currentItem = BudgetItemsList.get(position);
        holder.target_amount_txt.setText(currentItem.getBudget_amount());
        String date = RecyclerAdapter.DateConvertor(currentItem.getBudget_date(),false);
        holder.target_date_txt.setText(date);

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

    public static class TargetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView target_amount_txt;
        public TextView target_date_txt;
        OnTargetItemListner onTargetItemListner;
        public TargetViewHolder(@NonNull View itemView,OnTargetItemListner onTargetItemListner) {
            super(itemView);
            target_amount_txt = itemView.findViewById(R.id.targetAmount);
            target_date_txt = itemView.findViewById(R.id.targetDate);
            this.onTargetItemListner = onTargetItemListner;

            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTargetItemListner.OnTargetItemClick(getAdapterPosition());

        }

        @Override
        public boolean onLongClick(View v) {
            onTargetItemListner.OnTargetItemLongClick(getAdapterPosition());
            return false;
        }
    }
    public interface OnTargetItemListner
    {
        void OnTargetItemClick(int position);

        void OnTargetItemLongClick(int position);
    }
}
