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

public class updateCategory_custom extends AppCompatDialogFragment {

    public EditText updatedCategory;
    private UpdateCategoryListner listner;
    public String oldCategory;

    public updateCategory_custom(String oldCategory) {
        this.oldCategory = oldCategory;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.update_category,null);

        updatedCategory = view.findViewById(R.id.updatedCategory);
        updatedCategory.setText(oldCategory);

        builder.setView(view)
                .setTitle("Update Category")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(updatedCategory.getText().toString().trim().equalsIgnoreCase(""))
                        {
                            updatedCategory.setError("Enter a valid text");
                        }

                        String name = updatedCategory.getText().toString();
                        listner.UpdateCategory(name);

                    }
                });

        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listner = (UpdateCategoryListner) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "must implement UpdateCategoryListner");
        }
    }

    public interface UpdateCategoryListner
    {
        void UpdateCategory(String category);
    }
}
