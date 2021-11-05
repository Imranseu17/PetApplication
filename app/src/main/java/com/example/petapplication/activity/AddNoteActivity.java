package com.example.petapplication.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.petapplication.R;
import com.example.petapplication.databinding.ActivityAddNoteBinding;
import com.google.android.material.snackbar.Snackbar;
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

    ActivityAddNoteBinding addNoteBinding;
    String statusValue;
    Calendar calendar;
    int year,month,dayOfMonth;
    DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addNoteBinding = DataBindingUtil. setContentView(this,R.layout.activity_add_note);

        addNoteBinding. spinner.setItems("Open", "In-Progress", "Test", "Done");
        addNoteBinding. spinner.setOnItemSelectedListener
                (new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                statusValue = item;
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Note");
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
    }


    private void saveNote() {

        String title = addNoteBinding.editTitle.getText().toString().trim();
        String description = addNoteBinding.editDescription.getText().toString().trim();
        String status = statusValue;
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        String createdDate =  formatter.format(date);

        String deadlineDate = addNoteBinding.deadlineDate.getText().toString();

        if(title.isEmpty() || description.isEmpty()){
            Toast.makeText(this," Please insert a title and description",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_STATUS,status);
        data.putExtra(CREATED_DATE,createdDate);
        data.putExtra(DEADLINE,deadlineDate);

        setResult(RESULT_OK,data);
        finish();

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


}