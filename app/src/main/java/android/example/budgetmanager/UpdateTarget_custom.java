package android.example.budgetmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class UpdateTarget_custom extends AppCompatDialogFragment {

    private EditText updatedTarget;
    private UpdateTargetListner listner;
    public String oldTarget;

    public UpdateTarget_custom(String oldTarget) {
        this.oldTarget = oldTarget;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());



        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_update_target_custom,null);



           builder.setView(view)
                   .setTitle("Update Target")
                   .setNegativeButton("Cancel",null)
                   .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           String newTarget = updatedTarget.getText().toString();
                           listner.updateTarget(newTarget);


                       }
                   });

           updatedTarget = view.findViewById(R.id.updatedTarget);
           updatedTarget.setText(oldTarget);


           return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listner = (UpdateTargetListner) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "must implement UpdateTargetListner");
        }
    }

    public interface UpdateTargetListner
    {
       void updateTarget(String updatedTarget);
    }
}
