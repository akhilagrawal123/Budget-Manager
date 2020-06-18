package android.example.budgetmanager;

import android.os.Parcel;
import android.os.Parcelable;

public class BudgetItems implements Parcelable {
    private String mBudget_id;
    private String mBudget_date;
    private String mBudget_category;
    private String mBudget_amount;

    public BudgetItems(String budget_id, String budget_date, String budget_category, String budget_amount)
    {

        this.mBudget_id = budget_id;
        this.mBudget_date = budget_date;
        this.mBudget_category = budget_category;
        this.mBudget_amount = budget_amount;
    }

    protected BudgetItems(Parcel in) {
        mBudget_id = in.readString();
        mBudget_date = in.readString();
        mBudget_category = in.readString();
        mBudget_amount = in.readString();
    }

    public static final Creator<BudgetItems> CREATOR = new Creator<BudgetItems>() {
        @Override
        public BudgetItems createFromParcel(Parcel in) {
            return new BudgetItems(in);
        }

        @Override
        public BudgetItems[] newArray(int size) {
            return new BudgetItems[size];
        }
    };

    public String getBudget_id() {
        return mBudget_id;
    }

    public String getBudget_date() {
        return mBudget_date;
    }

    public String getBudget_category() {
        return mBudget_category;
    }

    public String getBudget_amount() {
        return mBudget_amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mBudget_id);
        dest.writeString(mBudget_date);
        dest.writeString(mBudget_category);
        dest.writeString(mBudget_amount);
    }
}
