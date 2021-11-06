package com.example.petapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.petapplication.R;
import com.example.petapplication.databinding.ActivityEditNoteBinding;
import com.example.petapplication.model.Note;
import com.example.petapplication.viewModel.NoteViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.codinginflow.architectureexample.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.codinginflow.architectureexample.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.codinginflow.architectureexample.EXTRA_DESCRIPTION";
    public static final String EXTRA_STATUS =
            "com.example.livedataroomviewmodel.EXTRA_STATUS";
    public static final String CREATED_DATE =
            "com.example.livedataroomviewmodel.CREATED_DATE";
    public static final String DEADLINE =
            "com.example.livedataroomviewmodel.DEADLINE";

    ActivityEditNoteBinding editNoteBinding;
    String statusValue;
    Calendar calendar;
    int year,month,dayOfMonth;
    DatePickerDialog datePickerDialog;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editNoteBinding = DataBindingUtil. setContentView(this,R.layout.activity_edit_note);

        // Full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        editNoteBinding. spinner.setItems("Open", "In-Progress", "Test", "Done");
        editNoteBinding. spinner.setOnItemSelectedListener
                (new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                        statusValue = item;
                    }
                });



        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            editNoteBinding.editTaskTitle.setText("Edit Tasks");
            editNoteBinding.editTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editNoteBinding.editDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            editNoteBinding.spinner.setText(intent.getStringExtra(EXTRA_STATUS));
            editNoteBinding.deadlineDate.setText(intent.getStringExtra(DEADLINE));
        } else {
            editNoteBinding.editTaskTitle.setText("Add Tasks");
        }
        calendar=Calendar.getInstance();
        editNoteBinding.calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateTimeField();

            }
        });
        editNoteBinding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });

        editNoteBinding.emailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailUpdate();
            }
        });

        editNoteBinding.phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumberUpdate();
            }
        });

        editNoteBinding.urlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlUpdate();
            }
        });
    }

    private void saveNote(){

        String title = editNoteBinding.editTitle.getText().toString().trim();
        String description = editNoteBinding.editDescription.getText().toString().trim();
        String status = statusValue;
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        String createdDate =  formatter.format(date);

        String deadlineDate = editNoteBinding.deadlineDate.getText().toString();

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

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if(id != -1)
            data.putExtra(EXTRA_ID,id);

        Note note = new Note(title,description,status,createdDate,deadlineDate);
        note.setId(id);
        noteViewModel.update(note);

     //   Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        saveSuccessfully();

        setResult(RESULT_OK,data);
      //  startActivity(new Intent(EditNoteActivity.this,MainActivity.class));
    }



    private void setDateTimeField() {
        Calendar newCalendar = calendar;
        dayOfMonth = newCalendar.get(Calendar.DAY_OF_MONTH);
        month = newCalendar.get(Calendar.MONTH);
        year = newCalendar.get(Calendar.YEAR);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                editNoteBinding.deadlineDate.
                        setText(dayOfMonth+"."+(monthOfYear+1)+"."+year);
            }

        }, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void saveSuccessfully(){
        Dialog dialog = new Dialog(EditNoteActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.save_successfully);


        AppCompatButton ok = dialog.findViewById(R.id.ok);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditNoteActivity.this,MainActivity.class));
                dialog.dismiss();
            }
        });

        dialog.show();


    }

    private void emailUpdate(){
        Dialog dialog = new Dialog(EditNoteActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.email_save);

        TextInputEditText emailText = dialog.findViewById(R.id.edit_mail);
        AppCompatButton saveEmail = dialog.findViewById(R.id.emailSave);

        String email = emailText.getText().toString();

        saveEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.isEmpty()){
                    Toast.makeText(EditNoteActivity.this," Please give E-mail",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.dismiss();
            }
        });

        dialog.show();


    }



    private void phoneNumberUpdate(){
        Dialog dialog = new Dialog(EditNoteActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.phone_number_save);

        TextInputEditText phoneNumberText = dialog.findViewById(R.id.edit_phone_number);
        AppCompatButton savePhone = dialog.findViewById(R.id.phoneSave);

        String phoneNumber = phoneNumberText.getText().toString();

        savePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoneNumber.isEmpty()){
                    Toast.makeText(EditNoteActivity.this," Please give Phone Number",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.dismiss();
            }
        });

        dialog.show();


    }

    private void urlUpdate(){
        Dialog dialog = new Dialog(EditNoteActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.url_save);

        TextInputEditText urlText = dialog.findViewById(R.id.edit_url);
        AppCompatButton saveURL = dialog.findViewById(R.id.saveURL);

        String url = urlText.getText().toString();

        saveURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(url.isEmpty()){
                    Toast.makeText(EditNoteActivity.this," Please give URL",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.dismiss();
            }
        });

        dialog.show();


    }
}