package android.example.cs496.ui.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.example.cs496.R;
import android.example.cs496.ui.main.fragment1.phonebook.Contact;
import android.example.cs496.ui.main.fragment1.phonebook.JsonTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class TabFragment1 extends Fragment{
    private ArrayList<Contact> ArrayListOfEdit = new ArrayList<>();
    private ArrayList<Contact> clone = new ArrayList<>();
    private String url = "http://143.248.36.219:8080/";
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_one, container, false);
        final Fragment fragment = this;
        final Activity activity = getActivity();
        final Context mcontext = getContext();

        JsonTask task = new JsonTask(ArrayListOfEdit, "LoadAll", activity, mcontext, fragment, view);
        task.execute(url);



        Button buttonInsert = (Button) view.findViewById(R.id.addContact);
        buttonInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = LayoutInflater.from(getContext()).inflate(R.layout.edit_box, null, false);
                builder.setView(view);

                final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_update);
                final EditText editTextName = (EditText) view.findViewById(R.id.edittext_dialog_name);
                final EditText editTextPN = (EditText) view.findViewById(R.id.edittext_dialog_pn);
                final EditText editTextAddress = (EditText) view.findViewById(R.id.edittext_dialog_address);
                final EditText editTextEmail = (EditText) view.findViewById(R.id.edittext_dialog_email);
                final EditText editTextBirthday = (EditText) view.findViewById(R.id.edittext_dialog_birthday);
                final EditText editTextMemo = (EditText) view.findViewById(R.id.edittext_dialog_memo);

                ButtonSubmit.setText("Update");

                final AlertDialog dialog = builder.create();

                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strName = editTextName.getText().toString();
                        String strPN = editTextPN.getText().toString();
                        String strAddress = editTextAddress.getText().toString();
                        String strEmail = editTextEmail.getText().toString();
                        String strBirthday = editTextBirthday.getText().toString();
                        String strMemo = editTextMemo.getText().toString();

                        Contact contact = new Contact(strName,strPN, strAddress,strEmail,strBirthday,strMemo);
                        JsonTask task = new JsonTask(ArrayListOfEdit, "Add",contact, activity, mcontext, fragment, getView(),0);
                        task.execute(url);
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        return view;
    }


}