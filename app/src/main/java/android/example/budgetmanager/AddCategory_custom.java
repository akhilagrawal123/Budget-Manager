package android.example.budgetmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddCategory_custom extends AppCompatDialogFragment {

    public EditText newCategory;
    private InsertCategoryListner listner;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_category,null);

        newCategory = view.findViewById(R.id.newCategory);

        builder.setView(view)
                .setTitle("Add Category")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(newCategory.getText().toString().trim().equalsIgnoreCase(""))
                        {
                            newCategory.setError("Enter a valid text");
                        }
                        else {

                            String category = newCategory.getText().toString();
                            listner.AddCategory(category);
                        }


                    }
                })
                .setNegativeButton("Cancel",null);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listner = (InsertCategoryListner) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "must implement InsertCategoryListner");
        }
    }

    public interface InsertCategoryListner
    {
        void AddCategory(String newCategory);
    }
}
