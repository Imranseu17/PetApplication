package com.example.petapplication.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.petapplication.R;
import com.example.petapplication.databinding.ActivityTaskDeatilsBinding;
import com.example.petapplication.model.Note;
import com.example.petapplication.viewModel.NoteViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class TaskDeatilsActivity extends AppCompatActivity {

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
    public static final String E_Mail =
            "com.example.livedataroomviewmodel.E_Mail ";
    public static final String PHONE_NUMBER =
            "com.example.livedataroomviewmodel.PHONE_NUMBER";
    public static final String URL =
            "com.example.livedataroomviewmodel.URL";

    ActivityTaskDeatilsBinding deatilsBinding;
    private NoteViewModel noteViewModel;
    public static final int EDIT_NOTE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deatilsBinding = DataBindingUtil. setContentView(this,R.layout.activity_task_deatils);
        // Full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        Intent intent = getIntent();

        deatilsBinding.createdDate.setText("Created date: "+intent.getStringExtra(CREATED_DATE));
        deatilsBinding.status.setText(intent.getStringExtra(EXTRA_STATUS));
        deatilsBinding.taskName.setText(intent.getStringExtra(EXTRA_TITLE));
        deatilsBinding.description.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        deatilsBinding.deadlineDate.setText(intent.getStringExtra(DEADLINE));

        Note note = intent.getParcelableExtra("note");

        deatilsBinding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteViewModel.delete(note);
                Toast.makeText( TaskDeatilsActivity.this,"Note Deleted",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        deatilsBinding.buttonEditNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskDeatilsActivity.this, EditNoteActivity.class);
                intent.putExtra(EditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(EditNoteActivity.EXTRA_TITLE, note.getTaskName());
                intent.putExtra(EditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(EditNoteActivity.EXTRA_STATUS, note.getStatus());
                intent.putExtra(EditNoteActivity.DEADLINE,note.getDeadline());
                intent.putExtra(EditNoteActivity.E_Mail,note.getEmail());
                intent.putExtra(EditNoteActivity.PHONE_NUMBER,note.getPhoneNumber());
                intent.putExtra(EditNoteActivity.URL,note.getUrl());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

        deatilsBinding.emailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailShow();
            }
        });

        deatilsBinding.phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumberShow();
            }
        });

        deatilsBinding.urlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlShow();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(EditNoteActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(EditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(EditNoteActivity.EXTRA_DESCRIPTION);
            String status  = data.getStringExtra(EditNoteActivity.EXTRA_STATUS);
            String deadline = data.getStringExtra(EditNoteActivity.DEADLINE);
            String email = data.getStringExtra(EditNoteActivity.E_Mail);
            String phoneNumber = data.getStringExtra(EditNoteActivity.PHONE_NUMBER);
            String url = data.getStringExtra(EditNoteActivity.URL);
            String createdDate = data.getStringExtra(EditNoteActivity.CREATED_DATE);

            Note note = new Note(title,description,status,createdDate,deadline,email,phoneNumber,url);
            note.setId(id);
            noteViewModel.update(note);

            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        }

        else
            Toast.makeText(this," Note not Updated ",Toast.LENGTH_SHORT).show();
    }

    private void emailShow(){
        Dialog dialog = new Dialog(TaskDeatilsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.email_save);

        TextInputEditText emailText = dialog.findViewById(R.id.edit_mail);
        AppCompatButton showEmail = dialog.findViewById(R.id.emailSave);

        Intent intent = getIntent();
        emailText.setText(intent.getStringExtra(E_Mail));

        showEmail.setText("OK");

        showEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }

        });

        dialog.show();


    }

    private void phoneNumberShow(){
        Dialog dialog = new Dialog(TaskDeatilsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.phone_number_save);

        TextInputEditText phoneNumberText = dialog.findViewById(R.id.edit_phone_number);
        AppCompatButton showPhone = dialog.findViewById(R.id.phoneSave);

        Intent intent = getIntent();
        phoneNumberText.setText(intent.getStringExtra(PHONE_NUMBER));

        showPhone.setText("OK");

        showPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }

    private void urlShow(){
        Dialog dialog = new Dialog(TaskDeatilsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.url_save);

        TextInputEditText urlText = dialog.findViewById(R.id.edit_url);
        AppCompatButton showURL = dialog.findViewById(R.id.saveURL);

        Intent intent = getIntent();
        urlText.setText(intent.getStringExtra(URL));

        showURL.setText("OK");

        showURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }
}