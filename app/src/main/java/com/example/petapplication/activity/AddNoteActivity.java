package com.example.petapplication.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.petapplication.R;
import com.example.petapplication.databinding.ActivityAddNoteBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity{

    public static final String EXTRA_TITLE =
            "com.example.livedataroomviewmodel.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.livedataroomviewmodel.EXTRA_DESCRIPTION";
    public static final String EXTRA_STATUS =
            "com.example.livedataroomviewmodel.EXTRA_STATUS";
    public static final String CREATED_DATE =
            "com.example.livedataroomviewmodel.CREATED_DATE";
    public static final String DEADLINE =
            "com.example.livedataroomviewmodel.DEADLINE";
    public static final String E_Mail =
            "com.example.livedataroomviewmodel.E_Mail ";
    public static final String PHONE_NUMBER =
            "com.example.livedataroomviewmodel.PHONE_NUMBER";
    public static final String URL =
            "com.example.livedataroomviewmodel.URL";

    ActivityAddNoteBinding addNoteBinding;
    String statusValue;
    Calendar calendar;
    int year,month,dayOfMonth;
    DatePickerDialog datePickerDialog;
    SharedPreferences pref ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addNoteBinding = DataBindingUtil. setContentView(this,R.layout.activity_add_note);

        // Full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);

        addNoteBinding. spinner.setItems("Open", "In-Progress", "Test", "Done");
        addNoteBinding. spinner.setOnItemSelectedListener
                (new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                statusValue = item;
            }
        });

        calendar=Calendar.getInstance();
        addNoteBinding.calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             setDateTimeField();

            }
        });

        addNoteBinding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });

        addNoteBinding.emailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailSave();
            }
        });

        addNoteBinding.phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumberSave();
            }
        });

        addNoteBinding.urlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlSave();
            }
        });
    }


    private void saveNote() {

        String title = addNoteBinding.editTitle.getText().toString().trim();
        String description = addNoteBinding.editDescription.getText().toString().trim();
        String status = statusValue;
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        String createdDate =  formatter.format(date);

        String deadlineDate = addNoteBinding.deadlineDate.getText().toString();


        String email = pref.getString("E-mail","");
        String phoneNumber = pref.getString("Phone-Number","");
        String url = pref.getString("URL","");

        if(title.isEmpty() || description.isEmpty() || status.isEmpty() ||
              deadlineDate.equals("00.00.0000")){
            Toast.makeText(this," All fill are required please fill up all fill",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_STATUS,status);
        data.putExtra(CREATED_DATE,createdDate);
        data.putExtra(DEADLINE,deadlineDate);
        data.putExtra(E_Mail,email);
        data.putExtra(PHONE_NUMBER,phoneNumber);
        data.putExtra(URL,url);

        setResult(RESULT_OK,data);
        saveSuccessfully();
    }
    private void saveSuccessfully(){
        Dialog dialog = new Dialog(AddNoteActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.save_successfully);


        AppCompatButton ok = dialog.findViewById(R.id.ok);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                dialog.dismiss();
            }
        });

        dialog.show();


    }


    private void setDateTimeField() {
        Calendar newCalendar = calendar;
        dayOfMonth = newCalendar.get(Calendar.DAY_OF_MONTH);
         month = newCalendar.get(Calendar.MONTH);
         year = newCalendar.get(Calendar.YEAR);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                addNoteBinding.deadlineDate.
                        setText(dayOfMonth+"."+(monthOfYear+1)+"."+year);
            }

        }, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void  emailSave(){
        Dialog dialog = new Dialog(AddNoteActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.email_save);

        TextInputEditText emailText = dialog.findViewById(R.id.edit_mail);
        AppCompatButton saveEmail = dialog.findViewById(R.id.emailSave);


        saveEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailText.getText().toString().equals("")){
                    Toast.makeText(AddNoteActivity.this," Please give E-mail",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences.Editor editor = pref.edit();
                editor.putString("E-mail", emailText.getText().toString());
                editor.commit();
                dialog.dismiss();
            }
        });

        dialog.show();


    }

    private void  phoneNumberSave(){
        Dialog dialog = new Dialog(AddNoteActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.phone_number_save);

        TextInputEditText phoneNumberText = dialog.findViewById(R.id.edit_phone_number);
        AppCompatButton savePhone = dialog.findViewById(R.id.phoneSave);



        savePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoneNumberText.getText().toString().equals("")){
                    Toast.makeText(AddNoteActivity.this," Please give Phone Number",
                            Toast.LENGTH_SHORT).show();
                return;
                }

                SharedPreferences.Editor editor = pref.edit();
                editor.putString("Phone-Number",  phoneNumberText.getText().toString());
                editor.commit();
                dialog.dismiss();
            }
        });

        dialog.show();


    }

    private void  urlSave(){
        Dialog dialog = new Dialog(AddNoteActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.url_save);

        TextInputEditText urlText = dialog.findViewById(R.id.edit_url);
        AppCompatButton saveURL = dialog.findViewById(R.id.saveURL);



        saveURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(urlText.getText().toString().equals("")){
                    Toast.makeText(AddNoteActivity.this," Please give URL",
                            Toast.LENGTH_SHORT).show();
                   return;
                }

                SharedPreferences.Editor editor = pref.edit();
                editor.putString("URL",  urlText.getText().toString());
                editor.commit();
                dialog.dismiss();
            }
        });

        dialog.show();


    }


}